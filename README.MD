# A fundamental microservice based on SpringCloud Alibaba 

## Introduction
This is a SpringCloud microservice skeleton project, which shows how to apply a microservice via SpringCloud Alibaba suite. 
The core of this project is using [Sentinel](https://github.com/alibaba/Sentinel) as flow control, traffic shaping, concurrency limiting, circuit breaking and system adaptive overload protection, to guarantee reliability and resilience for microservices, 
and using [Nacos](https://github.com/alibaba/nacos) as dynamic service discovery and configuration and service management.

You can develop your complex business code from this skeleton project.

## Usage
First of first, you should download the [Sentinel](https://github.com/alibaba/Sentinel/releases) and [Nacos](https://github.com/alibaba/nacos/releases). 
Notice that, before starting the last version Nacos, you should init the DB tables by using the tables scheme file which Nacos provides, and then follow Nacos startup guideline.

Secondly, modify Nacos and Sentinel config in every ```application.yaml``` file.

Finally, start up all service.


If you want to use this skeleton as an enterprise-level base project, there still has a lot of details you should figure out, like how to deploy Sentinel cluster and Nacos cluster, where should you use a MQ, use a cache(Redis) etc.

Then, Welcome to the fucking new world! Enjoy yourself.

## Dependencies combination

|                     | Version    |
|---------------------|------------|
| JDK                 | 17         |
| Springboot          | 3.3.12     |
| SpringCloud         | 2023.0.3   |
| SpringCloud Alibaba | 2023.0.3   |



