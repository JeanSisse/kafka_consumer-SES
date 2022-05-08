# Projeto básico de kafka-consumer para consumo e envio de mensagens para SES

# como rodar
- configurar o seu .bash_profile ou .bashrc:

```shell
code ~/.bash_profile
export AWS_ACCESS_KEY="SUA_KEY"
export AWS_SECRET_KEY="SEU_SECRET"
export AWS_ACCOUNT_ID="SEU_ID_DE_ACESSO"
source ~/.bash_profile
```

# Iniciar o servidor kafka (zookeeper e kafka)

# na raiz do projeto rodar o comando:

```shell
./buld.sh
./start.sh
```

- de preferência em terminal diferente, rodar o producer do kafka pela linha de comando:

```shell
~/kafka-3.1.0-src/bin/kafka-console-prod.sh --broker-list=localhost:9092 --topic="SEND_EMAIL"
```
