## Инструкция по запуску и использованию приложения-переводчика
1. Убедитесь, что на вашем компьютере установлен Docker 
2. Склонируйте репозиторий и в корневой директории проекта введите команду:
- docker-compose up --build
3. Перейдите на http://localhost:8080/api/v1/translate. Приложение поддерживает GET и POST запросы - Вы можете передать данные как в формате JSON, так и через параметры в URL:

**Пример для POST-запроса**
```
{
  "inputString" : "I love T-Bank",
  "sourceLang" : "en",
  "targetLang" : "ru"
}
```

**Пример для GET-запроса**
```
http://localhost:8080/api/v1/translate?inputString=I love T-Bank&sourceLang=en&targetLang=ru
```
