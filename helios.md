# Деплой на helios

## Зависимости на локалке

- `openjdk 17.0.12`
- `gradle`

Довольно просто установить все необходимое с помощью `sdkman`.

## Первичная настройка

Ставим репозиторий к себе:

```bash
git clone https://github.com/sultanowskii/workspace-booking
```

Подключаемся в helios:

```bash
ssh helios
```

На helios:

```bash
git clone https://github.com/sultanowskii/workspace-booking
cd workspace-booking/scripts
cat schema.sql indexes.sql functions.sql | psql
```

(опционально) вставляем сгенерированные тестовые данные:

```bash
cat gen.sql | psql
```

Добавляем индексы/триггеры:

```bash
cat indexes.sql triggers.sql | psql
```

Переходим в какую-нибудь удобную для нас директорию, создаем свой `.env`

```bash
cd /some/path
cp example.env .env
vim .env # устанавливаем необходимые параметры, выбираем какой-нибудь порт
```

Забрасываем `set-env.sh` для удобства:

```bash
scp set-env.sh helios:/some/path
```

## Сборка проекта (локалка)

```bash
gradle build
# По итогу, на helios app.jar, set-env.sh и .env должны оказаться в одной директории
scp app/build/libs/app.jar helios:/some/path
```

## Запуск проекта с пробросом порта на локалку

```bash
# с локалки
ssh s367553@helios.cs.ifmo.ru -p 2222 -L 8082:helios.cs.ifmo.ru:48080
# на гелиосе
cd /some/path
source set-env.sh
java -jar app.jar
```

- `2222` - ssh-порт
- `8082` - порт на локалке, можно выбрать любой доступный
- `48080` - порт на helios, нужно выбрать тот, что указан в `.env` на helios.

И теперь сервис доступен по `localhost:8082`.
