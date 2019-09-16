# Sample AWS Lambda Logging Application

This project contains a function that acts as a logging service. 
The application takes an event and stores it in the database, which can be reported on via a reporting service. 
This service maybe useful when event orchestration must be reported on but only a single service has the context of the request.

- LoggingFunction/src/main - Code for the application's Lambda function.
- events - A copy of the event that triggers the lambda  is in the body of the event. This file will need to be massaged to work.
- resource = an example of the actual data model used to log the request.
- template.yaml - A template that defines the application's AWS resources.


## Limitations
This application is a spike for a real world solution, but as a spike, several key aspects were not addressed.
* **Durability** Message storage if an Error occurs durring processing
* **Error Handling** Error handling should probably be more complete

## Prerequisites
- Create and Configure a database. App is configured for an RDS MySQL instance.
- Create an S3 Bucket to store deployments
- Configure API Gateway if exposing as a service.
- Deploy to Lamdba

## Configurations
Adjust following files;
template.yaml
  change RDS_HOSTNAME to your DB host.
  change RDS_DB_NAME to your database name.
  change RDS_USERNAME to your DB user/role username 
  change RDS_PASSWORD to your DB user/role password
cwds-logging-service.iml
  set bucketname to your S3 deployment bucket in AWS
/Users/tparker/projects/CWDS/LoggingFunction/src/main/resources/dbscripts/create_db.sql      
  Change database name
/Users/tparker/projects/CWDS/LoggingFunction/src/main/resources/dbscripts/create_tables.sql
  Change Database name

## Public vs Private
This app is configured for a API gateway. The api gateway wraps the json request with a Request object that contains the body, headers, etc.

The application was written to get the body from the request object. If running as a standalone Lambda, you can adjust the entry point function to take the EventRequest and simply call the lambda with that Request POJO. This would eliminate the manual conversion. 

Alternatively, a pojo representing the request object could be created which contains the EventRequest. 

## Development
The Following is the default documentation for working with the app. Make approprite changes where they apply.

If you prefer to use an integrated development environment (IDE) to build and test your application, you can use the AWS Toolkit.  
The AWS Toolkit is an open source plug-in for popular IDEs that uses the SAM CLI to build and deploy serverless applications on AWS. The AWS Toolkit also adds a simplified step-through debugging experience for Lambda function code. See the following links to get started.

* [IntelliJ](https://docs.aws.amazon.com/toolkit-for-jetbrains/latest/userguide/welcome.html)

## Deploy the sample application

The Serverless Application Model Command Line Interface (SAM CLI) is an extension of the AWS CLI that adds functionality for building and testing Lambda applications. It uses Docker to run your functions in an Amazon Linux environment that matches Lambda. It can also emulate your application's build environment and API.

To use the SAM CLI, you need the following tools.

* AWS CLI - [Install the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html) and [configure it with your AWS credentials].
* SAM CLI - [Install the SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
* Java8 - [Install the Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Docker - [Install Docker community edition](https://hub.docker.com/search/?type=edition&offering=community)

The SAM CLI uses an Amazon S3 bucket to store your application's deployment artifacts. If you don't have a bucket suitable for this purpose, create one. Replace `BUCKET_NAME` in the commands in this section with a unique bucket name.

```bash
AWS$ aws s3 mb s3://BUCKET_NAME
```

To prepare the application for deployment, use the `sam package` command.

```bash
AWS$ sam package \
    --output-template-file packaged.yaml \
    --s3-bucket BUCKET_NAME
```

The SAM CLI creates deployment packages, uploads them to the S3 bucket, and creates a new version of the template that refers to the artifacts in the bucket. 

To deploy the application, use the `sam deploy` command.

```bash
AWS$ sam deploy \
    --template-file packaged.yaml \
    --stack-name AWS \
    --capabilities CAPABILITY_IAM
```

After deployment is complete you can run the following command to retrieve the API Gateway Endpoint URL:

```bash
AWS$ aws cloudformation describe-stacks \
    --stack-name AWS \
    --query 'Stacks[].Outputs[?OutputKey==`HelloWorldApi`]' \
    --output table
``` 

## Use the SAM CLI to build and test locally

Build your application with the `sam build` command.

```bash
AWS$ sam build
```

The SAM CLI installs dependencies defined in `HelloWorldFunction/build.gradle`, creates a deployment package, and saves it in the `.aws-sam/build` folder.

Test a single function by invoking it directly with a test event. An event is a JSON document that represents the input that the function receives from the event source. Test events are included in the `events` folder in this project.

Run functions locally and invoke them with the `sam local invoke` command.

```bash
AWS$ sam local invoke HelloWorldFunction --event events/event.json
```

The SAM CLI can also emulate your application's API. Use the `sam local start-api` to run the API locally on port 3000.

```bash
AWS$ sam local start-api
AWS$ curl http://localhost:3000/
```

The SAM CLI reads the application template to determine the API's routes and the functions that they invoke. The `Events` property on each function's definition includes the route and method for each path.

```yaml
      Events:
        HelloWorld:
          Type: Api
          Properties:
            Path: /hello
            Method: get
```

## Add a resource to your application
The application template uses AWS Serverless Application Model (AWS SAM) to define application resources. AWS SAM is an extension of AWS CloudFormation with a simpler syntax for configuring common serverless application resources such as functions, triggers, and APIs. For resources not included in [the SAM specification](https://github.com/awslabs/serverless-application-model/blob/master/versions/2016-10-31.md), you can use standard [AWS CloudFormation](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-template-resource-type-ref.html) resource types.

## Fetch, tail, and filter Lambda function logs

To simplify troubleshooting, SAM CLI has a command called `sam logs`. `sam logs` lets you fetch logs generated by your deployed Lambda function from the command line. In addition to printing the logs on the terminal, this command has several nifty features to help you quickly find the bug.

`NOTE`: This command works for all AWS Lambda functions; not just the ones you deploy using SAM.

```bash
AWS$ sam logs -n HelloWorldFunction --stack-name AWS --tail
```

You can find more information and examples about filtering Lambda function logs in the [SAM CLI Documentation](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-logging.html).

## Unit tests

Tests are defined in the `HelloWorldFunction/src/test` folder in this project.

```bash
AWS$ cd HelloWorldFunction
HelloWorldFunction$ gradle test
```

## Cleanup

To delete the sample application and the bucket that you created, use the AWS CLI.

```bash
AWS$ aws cloudformation delete-stack --stack-name AWS
AWS$ aws s3 rb s3://BUCKET_NAME
```

## Resources

See the [AWS SAM developer guide](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html) for an introduction to SAM specification, the SAM CLI, and serverless application concepts.

Next, you can use AWS Serverless Application Repository to deploy ready to use Apps that go beyond hello world samples and learn how authors developed their applications: [AWS Serverless Application Repository main page](https://aws.amazon.com/serverless/serverlessrepo/)