# SistemaPedidos
##Como preparar a base de dados para rodar o sistema

Pare o serviço local do postgres da seguinte forma:
```
sudo service postgresql stop
```

Crie um container com postgres da seguinte forma:
```
docker run --name pg-docker -e POSTGRES_PASSWORD=docker -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgres/data postgres
```
