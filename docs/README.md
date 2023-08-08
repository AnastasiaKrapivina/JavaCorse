### Процедура запуска автотестов.
1. Склонировать [проект](https://github.com/AnastasiaKrapivina/JavaCorse)
2. Открыть [проект](https://github.com/AnastasiaKrapivina/JavaCorse) в IDEA
3. Открыть фаил [docker-compose.yml](../docker-compose.yml) и через терминал ввести команду `docker-compose up` 
4. Запустить через терминал SUT, для этого необходимо через терминал ввести команду `java -jar aqa-shop.jar`
5. В терминале ввести команду для запусков тестов `./gradlew test --info`
6. Посмотреть отчет о тестироовании запустив в терминале команду `./gradlew allureServe`