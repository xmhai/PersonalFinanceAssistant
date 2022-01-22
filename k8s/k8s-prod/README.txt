- mysql setup
Option 1. mysql on docker
mysql-pvc.yaml, mysql-config.yaml, mysql-node-port.yaml
are used to setup mysql in kubernetes

Option 2 local database (use ip)
mysql-service.yaml, mysql-endpoint.yaml
in development env, local mysql will be a better choice for easy management

Option 3 Cloud managed database service (Azure)

