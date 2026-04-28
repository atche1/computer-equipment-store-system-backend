# Computer Equipment Store System – Backend

Backend част на уеб базирана система за онлайн магазин за компютърна техника и услуги.

---

## 🛠 Използвани технологии

* Java
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)
* MySQL / MariaDB
* Flyway (database migrations)
* Maven
* JWT Authentication

---

## 🚀 Стартиране на проекта

### 1. Създаване на база данни

```sql
CREATE DATABASE `computer-equipment-store-system`;
```

### 2. Конфигурация

В `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/computer-equipment-store-system
spring.datasource.username=root
spring.datasource.password=
```

### 3. Стартиране

```bash
./mvnw spring-boot:run
```

Приложението ще бъде достъпно на:

```
http://localhost:8080
```

---

## 🗄 База данни

Структурата на базата данни се създава автоматично чрез **Flyway миграции** при стартиране на приложението.

---

## 📦 Демо данни

Проектът **не използва автоматично seed-ване на данни**.

За тестване са предоставени примерни данни в:

```
database/demo-data.sql
```

### Импорт на данните

След стартиране на приложението изпълнете:

```bash
mysql -u root "computer-equipment-store-system" < database/demo-data.sql
```

---

## 📌 Основни функционалности

* Регистрация и вход на потребители
* JWT базирана автентикация
* Управление на продукти
* Категории и атрибути
* Количка
* Поръчки
* Сервизни заявки
* Административен панел

---

## 🎓 Дипломна работа

**Онлайн магазин за компютърна техника и услуги**
