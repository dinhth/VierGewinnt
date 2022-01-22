FROM hseeberger/scala-sbt:8u312_1.6.1_3.1.0

RUN apt-get update && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6

WORKDIR /VierGewinnt
ADD . /VierGewinnt

CMD sbt run
