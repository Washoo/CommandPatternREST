#!/usr/bin/env bash

ALB_SERVICE=$(aws elbv2 describe-load-balancers | jq '.LoadBalancers[]' | jq -r '.LoadBalancerArn')
echo ${ALB_SERVICE}

echo $(aws elbv2 describe-listeners --load-balancer-arn arn:aws:elasticloadbalancing:us-east-1:573584040050:loadbalancer/app/weppa-alb/8cb59e344bcd5855 | jq -r '.Listeners[]' | jq -r '.ListenerArn')
