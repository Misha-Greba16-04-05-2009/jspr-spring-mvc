# Spring Web MVC — домашнее задание 2.3

Миграция REST API постов с сервлетов на Spring Web MVC + Embedded Tomcat.

## Запуск

```bash
mvn spring-boot:run
```

API: `http://localhost:8080/api/posts`

## Структура

- `PostController` — REST-контроллер (`/api/posts`)
- `PostService` — бизнес-логика
- `PostRepository` — in-memory хранилище

## Тестирование

Файл `src/test/request.http` — примеры запросов для IDE.
