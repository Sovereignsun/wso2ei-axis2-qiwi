# QiwiService

Для развёртывания в качестве Axis2 скомпонованного микросервиса в среде WSO2 EI

Этот автономный микросервис на фреймворке Axis2 предоставляет возможность полной интеграции с <b>QIWI</b> по средством
терминального протокола. Сервис позволяет развернуть его даже в чистой среде WSO2 EI (начиная с версии 6.1.0).

Основной класс сервиса в котором содержится список методов для работы с ним является ```ru.kubankredit.qiwi.Qiwi```.
Все операции поддерживают как <b>SOAP 1.1</b>, так и <b>SOAP 1.2</b> конвенции вызова в рамках заголовков
<i>SOAPAction</i> указанных в элементах ```"actionMapping"```.

### Подготовительные работы для настройки сервиса и доступа к АО "Киви"

#### <i>Настройка сервиса:</i>

Для настройки сервиса, после сборки проекта в ```*.aar``` файл, необходимо открыть его (файл является архивом)
и открыть файл настроек ```settings.yml```, в нём содержится список настроек сервиса для работы с протоколом Киви,
а также <b>url</b> до Киви сервиса, взаимодействия с базой данных и другими сервисами платёжного центра,
содержит список внутренних параметров.

Из основных блоков, изменению подлежат параметры:

- <i><b>Oracle база данных - настройки:</b></i>
  ``` yaml
  Oracle:
    url: "jdbc:oracle:thin:@ip:port/db" # Адрес соединения с базой данных Oracle (платёжный центр)
    username: "username" # Пользователь (владелец схемы)
    password: "password" # Пароль пользователя
  ```
- <i><b>GateContract сервис - настройки:</b></i>
  ``` yaml
  GateContract:
    url: "http://localhost:9008/soap/IGateContract" # Адрес сервиса договоров
    connectionTimeout: 10 # Таймаут соединения с сервисом (в секундах)
    readTimeout: 30 # Таймаут ожидания ответа от сервиса (в секундах)
  ```
- <i><b>GatePayments сервис - настройки:</b></i>
  ``` yaml
  GatePayments:
    url: "http://localhost:9010/soap/IGatePayments" # Адрес сервиса платежей
    connectionTimeout: 10 # Таймаут соединения с сервисом (в секундах)
    readTimeout: 30 # Таймаут ожидания ответа от сервиса (в секундах)
  ```
- <i><b>ActiveMQ брокер сообщений - настройки:</b></i>
  ``` yaml
  ActiveMQ:
    url: "tcp://localhost:61616" # Адрес на котором брокер сообщений ActiveMQ работает
    username: "admin" # Имя пользователя
    password: "admin" # Пароль
    consumeMessagesPerCall: 5 # Количество сообщений, которое за одну итерацию будет вычитано из брокера
    consumeInterval: 5 - # Интервал в секундах между запросами вычитки сообщений от брокера
    consumeActive: true # Признак активности потребителя сообщений от брокера
    paymentStatusQueueName: "QiwiGetPaymentStatus" # Название очереди для получения статуса платежа
  ```
- <i><b>АО "Киви" взаимодействие и интеграция - настройки:</b></i>
  ``` yaml
  Qiwi:
    url: "http://servicet.osmp.ru/xmlgate/xml.jsp" # Адрес взаимодействия с АО "Киви" (текущий тестовый)
    connectionTimeout: 10 # Таймаут соединения с сервисом (в секундах)
    readTimeout: 30 # Таймаут ожидания ответа от сервиса (в секундах)
    login: "dev0000" # Логин пользователя в АО "Киви"
    privateKeyPath: "/private.key" # Путь до приватного ключа (для подписания запросов в АО "Киви")
    keyIsResource: true # Признак того, что приватный ключ лежит в архиве сервиса (является ресурсом)
    publicKeyStoreType: "1" # Тип хранилища публичного ключа в АО "Киви" (константа)
    signAlgorithm: "MD5" # Алгоритм используемый для подписания пароля пользователя
    software: "Dealer v0" # Алиас программного средства используемого в АО "Киви" (константа)
    terminalId: "0000000" # Идентификатор терминала (если не был передан в запросе)
    userAgent: "KKBank" # Агентское имя отправителя запросов в АО "Киви"
    digitalSignAlgorithm: "SHA1withRSA" # Алгоритм подписания цифровой подписью запросов в АО "Киви"
    fixedAmount: "10.00" # Фиксированная сумма денег отправляемая в запросе проверке возможности добавления платежа
  ```

<i>Примечания:</i>

- Можно хранить приватный ключ вне сервиса, но тогда нужно в параметре ```PrivateKeyPath``` указать адрес до ключа и в
  параметре
  ```KeyIsResource``` поставить значение <b>false</b>
- Параметр ```TerminalId``` будет браться из настроек только если он не был передан в виде Query параметров в запросах
  <b>GetCharges</b>, <b>PossPayment</b>, и <b>AddPayment</b> от платёжного центра
- Если взаимодействие с АО "Киви" промышленными или тестовыми сервисами происходит не напрямую из этого сервиса,
  то необходимо путь до прокси-сервиса в параметре ```QiwiUrl```
- Если необходимо временно отключить считывание сообщений от брокера ActiveMQ, то следует переключить параметр
  ```consumeActive``` в значение <b>false</b>

#### <i>Настройка доступа к АО "Киви":</i>

Для настройки доступа до АО "Киви" необходимо предпринять несколько шагов. Основная инструкция в файле <i><b>
agt_cabinet_ru</b></i>.

1. В личном кабинете агента (<i>https://agt.qiwi.com </i> - промышленный, <i>https://agt-test.qiwi.com </i> - тестовый)
   на сайте АО "Киви" добавить персону и указать ей логин и добавить терминал c типом <b>"XML протокол"</b>.
   Для доступа к личному кабинету потребуется сертификат безопасности от АО "Киви".
2. В профиле терминала есть вкладка <b>"Персоны"</b>, там следует привязать к терминалу персону, которая будет
   использоваться для регистрации
   доступа по ключу к терминалу и в целом в запросах.
3. Создать для персоны одноразовый пароль в разделе <b>"Персоны"</b>, в профиле добавленной персоны или во вкладке <b>"
   Персоны"</b>
   профиля терминала. В тестовой среде его можно получить по
   адресу <i>https://agt-test.qiwi.com/system/person-messages </i>,
   а в промышленной среде на номер телефона.
4. Для текущего сервиса взаимодействия с Киви необходимо в настройки сервиса скопировать логин персоны и идентификатор
   терминала
   (не путать с серийным номером терминала).
5. Ключи формата ```PCKS#1``` следует запросить у отдела безопасности, который должен предоставить их в файлах
   <b>private.key</b> и <b>public.key</b> по примеру заголовков:

    * Публичный ключ:
      ``` markdown
      -----BEGIN PUBLIC KEY-----
      -----END PUBLIC KEY-----
      ```
    * Приватный ключ RSA:
      ``` markdown
      -----BEGIN RSA PRIVATE KEY-----
      -----END RSA PRIVATE KEY-----
      ```
6. Первый запрос ```SetPublicKey``` в сервис должен содержать в себе одноразовый пароль и публичный ключ, после чего
   можно будет
   отправлять запросы, которые будут подписываться с помощью закрытого ключа и отправляться в АО "Киви".
7. В запросе ```SetPublicKey``` в элемент <b>PublicKey</b> необходимо вставить текст из публичного ключа заключенный
   между
   ```BEGIN``` и ```END``` секциям ключа, а в элемент <b>UserPassword</b> необходимо вставить одноразовый пароль персоны.
8. Для настройки услуг платёжного центра и контроля состояния корректности настройки услуг по провайдерам предоставленным
   АО "Киви"
   необходимо делать запрос ```GetUIProviders```, к примеру в метод ```ProxyToQiwi``` в сервисе, который вернёт XML
   список провайдеров
   с настройками (пример запроса и ответа есть в ```src\main\resources\Examples```).

## Основные операции из ПЦ

#### Операция запроса начислений (GetCharges):

```xml

<operation name="GetCharges">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
    <actionMapping>http://ProxyService/IProxyService/GetCharges</actionMapping>
</operation>
```

Пример запроса и ответа есть в ```src\main\resources\Examples``` с префиксом ```GetCharges```

Пример (без данных) запроса обёрнутый в SOAP-Envelope версии SOAP 1.1:

```
curl -X POST -H "Content-Type: text/xml; charset=UTF-8" -H "SOAPAction: \"http://ProxyService/IProxyService/GetCharges\"" -d '<?xml version='"'1.0'"' encoding='"'utf-8'"'?><SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><SOAP-ENV:Body><GetCharges xmlns="http://ProxyService"/></SOAP-ENV:Body></SOAP-ENV:Envelope>' "http://localhost:8280/services/QiwiService?id=1243&terminal=97605235"
```

#### Операция предварительной проверки перед добавлением платежа (PossPayment):

```xml

<operation name="PossPayment">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
    <actionMapping>http://ProxyService/IProxyService/PossPayment</actionMapping>
</operation>
```

Пример запроса и ответа есть в ```src\main\resources\Examples``` с префиксом ```PossPayment```

Пример (без данных) запроса обёрнутый в SOAP-Envelope версии SOAP 1.1:

```
curl -X POST -H "Content-Type: text/xml; charset=UTF-8" -H "SOAPAction: \"http://ProxyService/IProxyService/PossPayment\"" -d '<?xml version='"'1.0'"' encoding='"'utf-8'"'?><SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><SOAP-ENV:Body><PossPayment xmlns="http://ProxyService"/></SOAP-ENV:Body></SOAP-ENV:Envelope>' "http://localhost:8280/services/QiwiService?id=1243&terminal=97605235"
```

#### Операция добавления платежа (AddPayment):

```xml

<operation name="AddPayment">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
    <actionMapping>http://ProxyService/IProxyService/AddPayment</actionMapping>
</operation>
```

Пример запроса и ответа есть в ```src\main\resources\Examples``` с префиксом ```AddPayment```

Пример (без данных) запроса обёрнутый в SOAP-Envelope версии SOAP 1.1:

```
curl -X POST -H "Content-Type: text/xml; charset=UTF-8" -H "SOAPAction: \"http://ProxyService/IProxyService/AddPayment\"" -d '<?xml version='"'1.0'"' encoding='"'utf-8'"'?><SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><SOAP-ENV:Body><AddPayment xmlns="http://ProxyService"/></SOAP-ENV:Body></SOAP-ENV:Envelope>' "http://localhost:8280/services/QiwiService?id=1243&terminal=97605235"
```

#### Примечание к основным методам:

Если не передавать <b>"terminal"</b> параметр в запросе, то сервис попытается взять его из настроек сервиса в
файле <i><b>"settings.properties"</b></i> из параметра <b>"TerminalId"</b>

В реализации метода ```AddPayment``` прописан функционал авторизации платежа и следом подтверждения платежа в рамках
одной интерации по запросу из ПЦ.

## Второстепенные операции

#### Операция выполнения прямых запросов (requests) до АО "Киви" минуя какую-либо обработку со стороны сервиса, но с подписанием с помощью сертификата и кодированием в Base64.

```xml

<operation name="ProxyToQiwi">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
</operation>
```

Пример запроса обёрнутый в SOAP-Envelope версии SOAP 1.2:

```
curl -X POST -H "Content-Type: application/soap+xml; charset=UTF-8; action=\"urn:ProxyToQiwi\"" -d '<?xml version='"'1.0'"' encoding='"'UTF-8'"'?><soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope"><soapenv:Body><request><client software="Dealer v0" terminal="10788430"/><agents><getBalance/></agents></request></soapenv:Body></soapenv:Envelope>' http://localhost:8280/services/QiwiService
```

#### Операция отправки публичного ключа PCKS#1 в АО "Киви"

```xml

<operation name="SetPublicKey">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
</operation>
```

Пример запроса обёрнутый в SOAP-Envelope версии SOAP 1.2:

```
curl -X POST -H "Content-Type: application/soap+xml; charset=UTF-8; action=\"urn:SetPublicKey\"" -d '<?xml version='"'1.0'"' encoding='"'UTF-8'"'?><soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope"><soapenv:Body>SetPublicKey><UserPassword/><PublicKey/></SetPublicKey></soapenv:Body></soapenv:Envelope>' http://localhost:8280/services/QiwiService
```

#### Операция получения информации о балансе на терминале. В запросе необходимо указать идентификатор терминала в виде цифрового значения.

```xml

<operation name="GetBalance">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
</operation>
```

Пример вызова:

```
curl -X GET "http://localhost:8280/services/QiwiService/GetBalance?terminal=[id]"
```

#### Операция получения информации о поставщиках услуг и их настрйоках в АО "Киви". В запросе необходимо указать идентификатор терминала в виде цифрового значения.

```xml

<operation name="GetUIProviders">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
</operation>
```

Пример вызова:

```
curl -X GET "http://localhost:8280/services/QiwiService/GetUIProviders?terminal=[id]"
```

#### Операция получения информации о версии сервиса.

```xml

<operation name="GetVersion">
    <messageReceiver class="org.apache.axis2.receivers.RawXMLINOutMessageReceiver"/>
</operation>
```

Пример вызова:

```
curl -X GET "http://localhost:8280/services/QiwiService/Version"
```

### <i>Примечание разработчика:</i>

Внутри сервиса в модуле ```Core``` есть параметр ```DeveloperMode``` который определяет режим
работы сервиса. В положении **true** запросы будут лететь в эмулятор внутри сервиса, а положении **false**
будут лететь напрямую по адресу конечных точек указанных в настройках.

## Как скомпилировать сервис

Из этой директории запустите команду:

```
mvn clean install
```

## Как запустить микросервис на WSO2 EI

Проследуйте в директорию ```<EI_HOME>/repository/deployment/server/axis2services``` и скопируйте в неё ```*.aar``` файл
скомпилированной версии микросервиса из директории ```/target```.
**AAR** сервис будет развёрнут внутри **WSO2 EI** после запуска.

Пример имени сервиса: ```QiwiService-1.0.0.aar```

## Как проверить сервис

Можно выполнить следующий запрос возвращающий версию сервиса

```
curl -X GET "http://localhost:8280/services/QiwiService/Version"
```

## Сценарии предоставленные АО "Киви" в рамках проведения тестирования в тестовой среде

1. Пример успешного платежа: 
   ```
   service="42" amount="10" account="1175689795"
   ```
2. Клиент выбрал не ту услугу для пополнения оператора сотовой связи 
   (Номер не принадлежит оператору): 
   ```
   service="42" amount="10" account="4748186694"
   ```
3. Клиент при пополнении услуги ввёл неверный номер договора/лс 
   (Неверный формат счета/телефона): 
   ```
   service="42" amount="10" account="11756897959"
   ```
4. Клиент при пополнении услуги не дописал цифру/букву номера договора/лс 
   (Неверный формат счета/телефона):
   ```
   service="42" amount="10" account="175689795"
   ```
5. Пополнение услуги с фиксированной минимальной и максимальной суммой платежа (Сумма слишком велика): 
   ```
   service="42" amount="15001" account="1175689795"
   ```
6. Пополнение услуги с фиксированной минимальной и максимальной суммой платежа (Сумма слишком мала): 
   ```
   service="42" amount="0.55" account="1175689795"
   ```
7. Оплата игр по логину (Успех):
   ```
   service="8439" amount="10" account="email@mail.ru"
   ```
8. Оплата по номеру транспондера (Неверный формат счета/телефона):
   ```
   service="5743" amount="10" account="10123456789"
   ```

### Базовые действия доступные в тестовой среде:

1. Управление персонами (ЛК);

2. Управление терминалами (ЛК);

3. Авторизация по цифровой подписи;

4. Получение структуры интерфейса оплаты;

5. Получение баланса;

6. Проведение платежей (проверка, отправка, запрос статуса).

7. Проверка запроса getRequiredPersonalData