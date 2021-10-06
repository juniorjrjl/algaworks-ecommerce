FROM gradle:7.2.0-jdk11

RUN apt-get update && apt-get install -qq -y --no-install-recommends

ENV INSTALL_PATH /algaworks-ecommerce

RUN mkdir -p $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY . .