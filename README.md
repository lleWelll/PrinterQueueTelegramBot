# PrinterQueueBot
This is a telegram bot that provides a interface for managing queue for 3D printers. 

## Description
This bot helps to manage queue for printers. Bot have 2 roles: user and damin. Users can join/leave/monitor queue, on other side admins can manage queue, add printers / plastic, send messages to users in queue and etc.

## Main Features
- Joining / leaving queue
- Browse current queue
- Adding unlimited amount of printers and plastic
- Modifying printers (availability, supported plastic)
- Users can upload their .stl files
- Notifying and messaging users in queue
- Adding / removing admins in whitelist

## Technical Stack
| Category        | Technology |
|------------------|------------|
| Language             | Java 17 |
| Framework        | Spring Boot, Spring Data JPA, Hibernate |
| Telegram Library | TelegramBots |
| DB  | MySQL |
| Deploy  | Docker, Docker Compose |
| Other           | Lombok, Jackson Databind |

## Architecture
All project divided into 2 main layers. ``model`` - main layer, that contains entities, dtos, repository and service classes. This layer responsible for work with DB and managing entities states.
``bot`` layer - layer that responsible for user interactions. Main class - ``telegramBot`` receives the updates and based on its type redirecting to handler. There are 3 handlers: 
- Callback handler
- Command handler
- Document handler
Commands divides into ``UserCommand``, ``AdminCommand`` and ``ExpectedCommand``

## How to run
(Current docker image of bot is compatible with MacOS and Linux, to add Windows create new image)
- Create bot from @BotFather and get bot token.  
- On your server create docker-compose and secret.env files (example in next section)
- Use ``docker-compose up``

## Docker-Compose file example:
```
services:
  db:
    image: mysql:8
    env_file: [secret.env]
    restart: on-failure
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping", "-h", "127.0.0.1", "--silent" ]
      interval: 5s
      retries: 10
    volumes:
      - mysql_data:/var/lib/mysql
    ports:
      - "3306:3306"

  app:
    image: llewell/printerqueuebot:latest
    restart: on-failure
    env_file:
      - secret.env
    expose:
      - "8080"
    ports:
      - "8080:8080"
    depends_on:
      db:
        condition: service_healthy
    volumes:
      - printer_uploads:/PrinterBot/uploads

volumes:
  mysql_data:
  printer_uploads:
```

## Secret.env file example:
```
MYSQL_DATABASE: printer_queue_db
MYSQL_ROOT_HOST: '%'
MYSQL_ROOT_PASSWORD: root

DB_URL="jdbc:mysql://db:3306/printer_queue_db"
DB_USERNAME="username_here"
DB_PASSWORD="password_here"
BOT_TOKEN="bot_toke_here"
ADMIN_1="telegram_username_here"
ADMIN_2="telegram_username_here"
ADMIN_3="telegram_username_here"
ADMIN_4="telegram_username_here"
STL_DIR_PATH="uploads/" #Don't change!
```
**!IMPORTANT!**

You must insert at least **one** admin. In ADMIN_1 write Telegram tag **without @**, for example ``lleWelll``. It's **case sensitive**!
You can add up to 4 admins, if you need more, you can use /addadmin command after deploy (added admins will dissapear after application restarting). If you need more admins on start, you need to change application.yml:
```
1) Clone project locally 
2) In secret.env add ADMIN_5
3) In application.yml add - ${ADMIN_5} in whitelist section
4) Use command mvn clean:install
5) Create new docker image
6) Use new docker image in your docker-compose
```
