# job4j_cinema
[![Build Status](https://app.travis-ci.com/hasover/job4j_cinema.svg?branch=master)](https://app.travis-ci.com/hasover/job4j_cinema)

* [Описание](#описание)
* [Технологии](#технологии)
* [Функционал](#функционал)
* [Контакты](#контакты)

## Описание
Сервис - Кинотеатр по покупке билетов в кинотеатр. Главная страница имеет вид таблицы, где показаны свободные и занятые места.
После того как пользователь выбрал место, осуществляется переход на страницу payment.html, где оформляется покупка.

## Технологии
* Java14
* PostgreSQL
* JDBC
* Servlet
* HTML, BOOTSTRAP, JS, Ajax
* Apache Tomcat
* Travis CI

## Функционал

### 1. Главная страница
![alt text](https://github.com/hasover/job4j_cinema/blob/master/images/%D0%BD%D0%B0%D1%87%D0%B0%D0%BB%D1%8C%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0.PNG)
### 2. Выбор билетов.
![alt text](https://github.com/hasover/job4j_cinema/blob/master/images/%D0%92%D1%8B%D0%B1%D0%BE%D1%80.PNG)
### 3. Подтверждение покупки.
![alt text](https://github.com/hasover/job4j_cinema/blob/master/images/%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BA%D0%B0.PNG)
### 3. Главная страница после покупки.
![alt text](https://github.com/hasover/job4j_cinema/blob/master/images/%D1%83%D0%B4%D0%B0%D1%87%D0%BD%D0%B0%D1%8F%20%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BA%D0%B0.PNG)
![alt text](https://github.com/hasover/job4j_cinema/blob/master/images/%D0%BD%D0%B5%D1%83%D0%B4%D0%B0%D1%87%D0%BD%D0%B0%D1%8F%20%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BA%D0%B0.PNG)

## Сборка приложения
- Для сборки приложения на вашем компьютере должны быть установлены:
    - JDK 14+
    - Maven
    - PostgreSQL
    - Tomcat
- Укажите настройки для подключения к БД в файле `db.properties`
- Выполните скрипт `db/schema.sql`
- Выполните команду `mvn package`
- Файл `target/job4j_cinema.war` скопируйте в webapp tomcat

Приложение будет доступно по адресу: http://localhost:8080/job4j_cinema

## Контакты
telegram: @hasover
