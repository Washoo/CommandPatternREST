#!/usr/bin/env bash
#more bash-friendly output for jq
 
CONTAINER_PORT=8082
AWS_REGION=us-east-1
DESIRE_COUNT=1
HEALTH_CHECK_URI=/actuator/health
STACK_NAME=${PROYECT_NAME}-${JOB}-${APPLICATION_NAME}
TARGET_GROUP=${PROYECT_NAME}-patterns-tg
TASK_NAME_ROLE=${STACK_NAME}-task-role
CONTAINER_TASK=${STACK_NAME}-container
TASK_DEFINITION=${CONTAINER_TASK}-family
CPU_AMOUNT=250

setup_git() {

    aws configure set aws_access_key_id "${AWS_ACCESS_KEY}"
    aws configure set aws_secret_access_key "${AWS_SECRET_KEY}"
    aws configure set default.region ${AWS_REGION}
    aws configure set default.output json
}

save_ecr_image() {

  eval $(aws ecr get-login --region ${AWS_REGION} --no-include-email)
  docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${PROYECT_NAME}/${APPLICATION_NAME}:${CIRCLE_BUILD_NUM}
}

deployment() {

  CLUSTER=$(aws ecs list-clusters | jq .clusterArns[0])
  VPC=$(aws ec2 describe-vpcs | jq '.Vpcs[0]' | jq .'VpcId')
  SERVICE_LISTENER=$(aws elbv2 describe-load-balancers | jq '.LoadBalancers[]' | jq '.LoadBalancerArn')

  PARAMETERS_DEFINITION="ParameterKey=Listener,ParameterValue=\"${SERVICE_LISTENER}\" \
                         ParameterKey=Cluster,ParameterValue=\"${CLUSTER}\" \
                         ParameterKey=VPC,ParameterValue=\"${VPC}\" \
                         ParameterKey=ServiceRole,ParameterValue=\"${SERVICE_ROLE}\" \
                         ParameterKey=ContainerTaskName,ParameterValue=\"${CONTAINER_TASK}\" \
                         ParameterKey=ContainerPort,ParameterValue=\"${CONTAINER_PORT}\" \
                         ParameterKey=HealthCheckUri,ParameterValue=\"${HEALTH_CHECK_URI}\" \
                         ParameterKey=TaskRoleName,ParameterValue=\"${TASK_NAME_ROLE}\" \
                         ParameterKey=TargetGroupName,ParameterValue=\"${TARGET_GROUP}\" \
                         ParameterKey=TaskDefinitionFamily,ParameterValue=\"${TASK_DEFINITION}\" \
                         ParameterKey=ApplicationName,ParameterValue=\"${APPLICATION_NAME}\" \
                         ParameterKey=DesiredCount,ParameterValue=\"${DESIRE_COUNT}\" \
                         ParameterKey=ContainerImage,ParameterValue=\"${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${PROYECT_NAME}/${APPLICATION_NAME}:${CIRCLE_BUILD_NUM}\" \
                        "

  aws cloudformation list-stacks --stack-status-filter CREATE_COMPLETE UPDATE_COMPLETE UPDATE_ROLLBACK_COMPLETE | jq '.StackSummaries[].StackName' | grep ${STACK_NAME}
  if [[ $? = 1 ]]; then
    aws cloudformation create-stack --stack-name ${STACK_NAME} --template-body file://deploy.yml --capabilities CAPABILITY_NAMED_IAM --parameters ${PARAMETERS_DEFINITION}
  else
    aws cloudformation update-stack --stack-name ${STACK_NAME} --template-body file://deploy.yml --capabilities CAPABILITY_NAMED_IAM --parameters ${PARAMETERS_DEFINITION}
  fi
}

setup_git

save_ecr_image

deployment
