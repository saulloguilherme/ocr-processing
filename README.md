## 📚 Projeto de Estudo: Microserviços com Kafka + OCR
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green.svg)](https://spring.io/projects/spring-boot)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-black.svg)](https://kafka.apache.org/)
[![MinIO](https://img.shields.io/badge/MinIO-Object%20Storage-red.svg)](https://min.io/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)](https://www.docker.com/)

Este projeto tem como objetivo estudar e praticar a arquitetura de microserviços utilizando Apache Kafka para comunicação assíncrona, enquanto explora o processamento de OCR com Tesseract.

## 🎯 Objetivo Principal

Projeto de estudo implementando uma arquitetura de microserviços com **Apache Kafka** para comunicação assíncrona. O objetivo é realizar OCR (Reconhecimento Óptico de Caracteres) em notas fiscais (NF-e) usando Tesseract, demonstrando na prática como serviços desacoplados se comunicam via eventos.


## 🏗️ Visão Geral da Arquitetura

O sistema é dividido em três camadas principais, cada uma rodando em seus próprios containers Docker:

### Camada de Infraestrutura (/docker-compose.yml):

MinIO: Armazenamento de objetos compatível com S3 para guardar as notas fiscais (PDFs/Imagens) enviadas.

Kafka & Zookeeper: Barramento de mensagens para toda comunicação assíncrona entre os serviços.

### Camada de API (/ocr-api/):

Aplicação Spring Boot: Expõe endpoints REST para receber os arquivos de nota fiscal.

PostgreSQL: Banco de dados relacional para armazenar metadados das notas, status do processamento e produtos extraídos.

Função: Gerencia uploads, salva arquivos no MinIO, persiste estado inicial e produz/consome eventos Kafka.

### Camada Worker (/ocr-listener/):

Aplicação Spring Boot: Um consumidor Kafka dedicado.

Função: Escuta eventos de novas notas, baixa o arquivo do MinIO, executa OCR com Tesseract e publica os resultados de volta via Kafka. Não tem acesso direto ao banco de dados, depende apenas de eventos.

## Funcionalidades Principais

- Processamento Assíncrono: Upload não bloqueante; usuário recebe confirmação imediata enquanto OCR roda em background.

- Baixo Acoplamento: API e Worker são independentes e se comunicam apenas via Kafka. O Worker não precisa conhecer a estrutura do banco.

- Atualizações Baseadas em Eventos: Worker publica resultados no Kafka, e a API consome para atualizar o banco, completando o fluxo.

- Ambiente Containerizado: Setup completo com Docker para fácil desenvolvimento e teste local.

- Foco em Estudo: Separação clara de responsabilidades, demonstrando um padrão prático de microserviços orientados a eventos.

## Tecnologias Utilizadas

- Backend: Java 21, Spring Boot 3.2

- Mensageria: Apache Kafka

- Armazenamento: MinIO (Objetos), PostgreSQL (Banco Relacional)

- Motor de OCR: Tesseract (integrado via Tess4J)

- Containerização: Docker, Docker Compose

## Como Executar o Projeto

Siga os passos abaixo para subir todo o ambiente localmente.
Pré-requisitos

    Docker e Docker Compose instalados na máquina.

    Java 17 para desenvolvimento local.

### Execução Passo a Passo

Clone o repositório
```bash
git clone https://github.com/saulloguilherme/ocr-processing.git
cd ocr-processing
```

Suba a Infraestrutura (Kafka e MinIO)
Esse comando cria a rede compartilhada ocr-network que todos os serviços vão usar.

```bash
docker compose up -d
```
Aguarde alguns segundos para Kafka e MinIO inicializarem completamente.

Suba o Serviço de API (com PostgreSQL)
Este serviço se conecta à rede e infraestrutura existentes.

```bash
cd ocr-api
docker compose up -d
cd ..
```

Suba o Worker (Listener)

```bash
cd ocr-listener
docker compose up -d
cd ..
```

Verifique se todos os containers estão rodando
```bash
docker ps
```
Você deve ver containers para: minio, kafka, zookeeper, postgres, ocr-api e ocr-listener.

### Acessando os Serviços

Endpoint da API: http://localhost:8080

| Método | Endpoint          | Descrição                                                           | Parâmetros                          | Respostas                                                                                                                                            |
|--------|-------------------|---------------------------------------------------------------------|-------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST   | `/invoice`        | Envia um arquivo (PDF/imagem) de nota fiscal para processamento OCR | `file` (MultipartFile, obrigatório) | **202 Accepted** – Processamento aceito, retorna `InvoiceResponseDTO` com ID e status `PENDING`<br>**400 Bad Request** – Arquivo inválido ou ausente |
| GET    | `/invoice/{uuid}` | Consulta o status e os dados extraídos de uma nota pelo seu UUID    | `uuid` (UUID, na URL)               | **200 OK** – Retorna `InvoiceResponseDTO` completo (incluindo produtos se `COMPLETED`)<br>**404 Not Found** – Nenhuma nota encontrada com esse UUID  |

Console do MinIO: http://localhost:9001 (Login: minioadmin / minioadmin)

PostgreSQL: localhost:5432 (Database: ocrdb, Usuário: ocruser, Senha: ocrpass)

Exemplo: Upload de uma Nota Fiscal

## Como Funciona a Comunicação

1 - Upload: Cliente envia arquivo para POST /invoice.

2 - Armazenamento: API salva arquivo no MinIO e registra no PostgreSQL com status PENDING.

3 - Publicação de Evento: API publica um InvoiceEventRequest no tópico invoice-ocr-request do Kafka. Esse evento contém o storagePath (ex: invoices/uuid.pdf).

4 - Processamento: O Worker, ouvindo o mesmo tópico, consome o evento. Usa o storagePath para baixar o arquivo do MinIO.

5 - OCR: Worker executa Tesseract no arquivo para extrair texto e identificar produtos.

6 - Evento de Resultado: Worker publica um InvoiceEventResponse no tópico invoice-ocr-result do Kafka, contendo os dados extraídos e o storagePath original.

7 - Atualização do Banco: A API, ouvindo o tópico de resultado, consome a resposta, localiza a nota pelo storagePath e atualiza o registro no PostgreSQL com os produtos extraídos e status COMPLETED.