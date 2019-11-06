#!/usr/bin/env bash

VPC=$(aws ec2 describe-vpcs | jq '.Vpcs[0]' | jq .'VpcId')
echo $VPC
