package ru.kubankredit.qiwi.emulators;

import ru.kubankredit.qiwi.core.exceptions.AbstractException;

import java.util.Random;

@SuppressWarnings("RedundantThrows")
public class QiwiHTTPEmulator {

    private String generateRandomUID() {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(number);
    }

    public String setPublicKey() throws AbstractException {
        final String[] listOfResponses = {
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                        "<response result=\"0\">\n" +
                        "\t<persons>\n" +
                        "\t\t<setPublicKey result=\"150\"\n" +
                        "\t\t              result-description=\"Неверный пароль\"/>\n" +
                        "\t</persons>\n" +
                        "</response>\n",
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                        "<response result=\"150\">Неверный пароль или нет прав на этот терминал</response>\n",
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                        "<response result=\"0\">\n" +
                        "\t<persons>\n" +
                        "\t\t<setPublicKey result=\"0\"/>\n" +
                        "\t</persons>\n" +
                        "</response>\n"
        };
        Random random = new Random();
        int index = random.nextInt(listOfResponses.length);

        return listOfResponses[index];
    }

    @SuppressWarnings("ConstantConditions")
    public String getBalance() throws AbstractException {
        final String[] listOfResponses = {
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                        "<response result=\"0\">\n" +
                        "\t<agents>\n" +
                        "\t\t<getBalance result=\"0\">\n" +
                        "\t\t\t<agent-id>5193728</agent-id>\n" +
                        "\t\t\t<balance>1000000.00</balance>\n" +
                        "\t\t\t<tree-balance>1010000.00</tree-balance>\n" +
                        "\t\t\t<overdraft>-10000.00</overdraft>\n" +
                        "\t\t</getBalance>\n" +
                        "\t</agents>\n" +
                        "</response>\n"
        };
        Random random = new Random();
        int index = random.nextInt(listOfResponses.length);

        return listOfResponses[index];
    }

    public String checkPaymentRequisites(Boolean fatal) throws AbstractException {
        final String fatalFalse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response result=\"0\">\n" +
                "  <providers>\n" +
                "    <checkPaymentRequisites result=\"0\">\n" +
                "      <payment date=\"2023-07-27T11:15:14+03:00\" fatal=\"false\" id=\"1\" result=\"0\" saved=\"false\" status=\"3\" uid=\"" + generateRandomUID() + "\">\n" +
                "        <extras AUTH_HOST=\"s1250.qiwi.com\" BEE_ID=\"896468\" MTS_TXN_ID=\"754334\" PARSER_HOST=\"s1250.qiwi.com\" PRV_TXN_ID=\"645719\"/>\n" +
                "      </payment>\n" +
                "    </checkPaymentRequisites>\n" +
                "  </providers>\n" +
                "</response>";
        final String fatalTrue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response result=\"0\">\n" +
                "  <providers>\n" +
                "    <checkPaymentRequisites result=\"0\">\n" +
                "      <payment fatal=\"true\" id=\"2\" result=\"4\" saved=\"false\" status=\"0\" uid=\"" + generateRandomUID() + "\">\n" +
                "        <extras PARSER_HOST=\"s1250.qiwi.com\"/>\n" +
                "      </payment>\n" +
                "    </checkPaymentRequisites>\n" +
                "  </providers>\n" +
                "</response>";

        if (fatal) {
            return fatalTrue;
        } else {
            return fatalFalse;
        }
    }

    public String authorizePayment(Boolean fatal) throws AbstractException {
        final String fatalFalse = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<response result=\"0\">\n" +
                "\t<providers>\n" +
                "\t\t<authorizePayment result=\"0\">\n" +
                "\t\t\t<payment date=\"2021-09-01T15:54:38+04:00\"\n" +
                "\t\t\t         id=\"301\"\n" +
                "\t\t\t         result=\"0\"\n" +
                "\t\t\t         fatal=\"false\"\n" +
                "\t\t\t         status=\"3\"\n" +
                "\t\t\t         uid=\"" + generateRandomUID() + "\">\n" +
                "\t\t\t\t<extras AUTH_HOST=\"app8.osmp.ru\"\n" +
                "\t\t\t\t        PARSER_HOST=\"app8.osmp.ru\"/>\n" +
                "\t\t\t</payment>\n" +
                "\t\t</authorizePayment>\n" +
                "\t</providers>\n" +
                "</response>";
        final String fatalTrue = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<response result=\"0\">\n" +
                "\t<providers>\n" +
                "\t\t<authorizePayment result=\"0\">\n" +
                "\t\t\t<payment date=\"2010-09-01T15:54:38+04:00\"\n" +
                "\t\t\t         id=\"302\"\n" +
                "\t\t\t         result=\"20\"\n" +
                "\t\t\t         fatal=\"true\"\n" +
                "\t\t\t         status=\"0\"\n" +
                "\t\t\t\t\t uid=\"" + generateRandomUID() + "\">\n" +
                "\t\t\t\t<extras AUTH_HOST=\"app8.osmp.ru\"\n" +
                "\t\t\t\t        PARSER_HOST=\"app8.osmp.ru\"/>\n" +
                "\t\t\t</payment>\n" +
                "\t\t</authorizePayment>\n" +
                "\t</providers>\n" +
                "</response>";
        if (fatal) {
            return fatalTrue;
        } else {
            return fatalFalse;
        }
    }

    public String confirmPayment(Boolean fatal) throws AbstractException {
        final String fatalFalse = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<response result=\"0\">\n" +
                "\t<providers>\n" +
                "\t\t<confirmPayment result=\"0\">\n" +
                "\t\t\t<payment date=\"2010-09-01T15:57:34+04:00\"\n" +
                "\t\t\t         id=\"301\"\n" +
                "\t\t\t         result=\"0\"\n" +
                "\t\t\t         fatal=\"false\"\n" +
                "\t\t\t         status=\"1\"\n" +
                "\t\t\t         uid=\"" + generateRandomUID() + "\">\n" +
                "\t\t\t\t<extras AUTH_HOST=\"app8.osmp.ru\"/>\n" +
                "\t\t\t</payment>\n" +
                "\t\t</confirmPayment>\n" +
                "\t</providers>\n" +
                "</response>";
        final String fatalTrue = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<response result=\"0\">\n" +
                "\t<providers>\n" +
                "\t\t<confirmPayment result=\"0\">\n" +
                "\t\t\t<payment date=\"2010-09-01T15:57:34+04:00\"\n" +
                "\t\t\t         id=\"302\"\n" +
                "\t\t\t         result=\"20\"\n" +
                "\t\t\t         fatal=\"true\"\n" +
                "\t\t\t         status=\"0\"\n" +
                "\t\t\t         uid=\"" + generateRandomUID() + "\">\n" +
                "\t\t\t\t<extras AUTH_HOST=\"app8.osmp.ru\"/>\n" +
                "\t\t\t</payment>\n" +
                "\t\t</confirmPayment>\n" +
                "\t</providers>\n" +
                "</response>";

        if (fatal) {
            return fatalTrue;
        } else {
            return fatalFalse;
        }
    }

    public String getPaymentStatus(Boolean fatal, String pmntId) throws AbstractException {
        final String fatalFalse = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<response result=\"0\">\n" +
                "\t<providers>\n" +
                "\t\t<getPaymentStatus result=\"0\">\n" +
                "\t\t\t<payment date=\"2022-12-04T19:23:10+04:00\"\n" +
                "\t\t\t         fatal=\"false\"\n" +
                "\t\t\t         id=\"" + pmntId + "\"\n" +
                "\t\t\t         result=\"0\"\n" +
                "\t\t\t         status=\"2\"\n" +
                "\t\t\t         uid=\"10625592616002\">\n" +
                "\t\t\t\t<extras AUTH_HOST=\"app2.osmp.ru\"\n" +
                "\t\t\t\t        PARSER_HOST=\"app5.osmp.ru\"/>\n" +
                "\t\t\t</payment>\n" +
                "\t\t</getPaymentStatus>\n" +
                "\t</providers>\n" +
                "</response>";
        final String fatalTrue = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<response result=\"0\">\n" +
                "\t<providers>\n" +
                "\t\t<getPaymentStatus result=\"0\">\n" +
                "\t\t\t<payment date=\"2022-12-04T19:23:10+04:00\"\n" +
                "\t\t\t         fatal=\"true\"\n" +
                "\t\t\t         id=\"" + pmntId + "\"\n" +
                "\t\t\t         result=\"15\"\n" +
                "\t\t\t         status=\"0\"\n" +
                "\t\t\t         uid=\"10625592616002\">\n" +
                "\t\t\t\t<extras AUTH_HOST=\"app2.osmp.ru\"\n" +
                "\t\t\t\t        PARSER_HOST=\"app5.osmp.ru\"/>\n" +
                "\t\t\t</payment>\n" +
                "\t\t</getPaymentStatus>\n" +
                "\t</providers>\n" +
                "</response>";

        if (fatal) {
            return fatalTrue;
        } else {
            return fatalFalse;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public String getUIProviders() throws AbstractException {
        final String[] listOfResponses = {
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<response result=\"0\">\n" +
                        "\t<providers>\n" +
                        "\t\t<getUIProviders result=\"0\">\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Сотовая св.\"\n" +
                        "\t\t\t          grpId=\"20\"\n" +
                        "\t\t\t          id=\"1\"\n" +
                        "\t\t\t          inn=\"7740000076\"\n" +
                        "\t\t\t          jDocDate=\"18.07.2003\"\n" +
                        "\t\t\t          jDocNum=\"2599/03\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ОАО &quot;Мобильные ТелеСистемы&quot;\"\n" +
                        "\t\t\t          keywords=\"mts|МТС\"\n" +
                        "\t\t\t          lName=\"ОАО &quot;Мобильные ТелеСистемы&quot;\"\n" +
                        "\t\t\t          logo=\"logos/2828106606373027275\"\n" +
                        "\t\t\t          logo_crc=\"523D408D\"\n" +
                        "\t\t\t          logo_size=\"1606\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\".01\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          receiptName=\"Сотовая связь\"\n" +
                        "\t\t\t          sName=\"МТС \"\n" +
                        "\t\t\t          supportPhone=\"+78002500890\"\n" +
                        "\t\t\t          tag=\"Not exist PDF_CANCELS,овал,visible,Запрет отмен без предоставления документов,ranges,sms_advert,Аффилированные провайдеры МТС,Провайдер выведен в Рапиду,Неправильные провы\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"523D408D\"\n" +
                        "\t\t\t\t\t      path=\"logos/2828106606373027275\"\n" +
                        "\t\t\t\t\t      size=\"1606\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams>\n" +
                        "\t\t\t\t\t<param name=\"_receipt_\"\n" +
                        "\t\t\t\t\t       value=\"Платеж принят без акцептования МТС \\nПлатёж может быть зачислен на счёт с задержкой\"/>\n" +
                        "\t\t\t\t</constParams>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"104\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Номер телефона вводится без '8'\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"(&lt;!^\\\\d+${3}&gt;)&lt;!^\\\\d+${3}&gt;-&lt;!^\\\\d+${2}&gt;-&lt;!^\\\\d+${2}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^.{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Сотовая св.\"\n" +
                        "\t\t\t          grpId=\"20\"\n" +
                        "\t\t\t          id=\"2\"\n" +
                        "\t\t\t          inn=\"7713076301\"\n" +
                        "\t\t\t          jDocDate=\"30.06.2008\"\n" +
                        "\t\t\t          jDocNum=\"06782/08\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ОАО &quot;Вымпел-Коммуникации&quot;\"\n" +
                        "\t\t\t          keywords=\"Пчела|Вымпел|Белайн|BILAIN\"\n" +
                        "\t\t\t          lName=\"ОАО &quot;Вымпел-Коммуникации&quot;\"\n" +
                        "\t\t\t          logo=\"logos/5312536625788073673\"\n" +
                        "\t\t\t          logo_crc=\"60C47221\"\n" +
                        "\t\t\t          logo_size=\"1850\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\".01\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          receiptName=\"Сотовая связь\"\n" +
                        "\t\t\t          sName=\"билайн \"\n" +
                        "\t\t\t          supportPhone=\"0611\"\n" +
                        "\t\t\t          tag=\"visible,ranges,Провайдеры для запрета ВК в офисах Билайн, RCTUNE-6595,sms_advert,Провайдер выведен в Рапиду,Неправильные провы\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"60C47221\"\n" +
                        "\t\t\t\t\t      path=\"logos/5312536625788073673\"\n" +
                        "\t\t\t\t\t      size=\"1850\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams>\n" +
                        "\t\t\t\t\t<param name=\"_receipt_\"\n" +
                        "\t\t\t\t\t       value=\"Оплатили не тот номер или хотите узнать статус платежа – звоните 07222 или beeline.ru/op\"/>\n" +
                        "\t\t\t\t</constParams>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"376\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Номер телефона вводится без '8'\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"(&lt;!^\\\\d+${3}&gt;)&lt;!^\\\\d+${3}&gt;-&lt;!^\\\\d+${2}&gt;-&lt;!^\\\\d+${2}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Сотовая св.\"\n" +
                        "\t\t\t          grpId=\"20\"\n" +
                        "\t\t\t          id=\"9\"\n" +
                        "\t\t\t          inn=\"7705348953\"\n" +
                        "\t\t\t          jDocDate=\"14.04.2004\"\n" +
                        "\t\t\t          jDocNum=\"053/04\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Матрикс Телеком&quot;\"\n" +
                        "\t\t\t          keywords=\"Матрикс|Matrix|мат рикс|Matriks\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Матрикс Телеком&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/matrix.gif\"\n" +
                        "\t\t\t          logo_crc=\"D4A9278C\"\n" +
                        "\t\t\t          logo_size=\"2054\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          receiptName=\"Сотовая связь\"\n" +
                        "\t\t\t          sName=\"Матрикс Телеком\"\n" +
                        "\t\t\t          supportPhone=\"+74955445555\"\n" +
                        "\t\t\t          tag=\"visible,ranges,161_ФЗ,sms_advert\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"D4A9278C\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/matrix.gif\"\n" +
                        "\t\t\t\t\t      size=\"2054\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"643\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Номер телефона вводится без '8'\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"(&lt;!^\\\\d+${3}&gt;)&lt;!^\\\\d+${3}&gt;-&lt;!^\\\\d+${2}&gt;-&lt;!^\\\\d+${2}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"НТВ Плюс\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Телевидение\"\n" +
                        "\t\t\t          grpId=\"40\"\n" +
                        "\t\t\t          id=\"20\"\n" +
                        "\t\t\t          inn=\"7703121379\"\n" +
                        "\t\t\t          jDocDate=\"07.08.2002\"\n" +
                        "\t\t\t          jDocNum=\"Н-ДУ-060802-ЖАО-01\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ОАО &quot;НТВ-Плюс&quot;\"\n" +
                        "\t\t\t          keywords=\"НТВ|NTV|ntvplus|нтвплюс\"\n" +
                        "\t\t\t          lName=\"ОАО &quot;НТВ-Плюс&quot;\"\n" +
                        "\t\t\t          logo=\"logos/1635500829276258839\"\n" +
                        "\t\t\t          logo_crc=\"A8D5600F\"\n" +
                        "\t\t\t          logo_size=\"1676\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Телевидение\"\n" +
                        "\t\t\t          sName=\"НТВ +\"\n" +
                        "\t\t\t          supportPhone=\"+74957556789\"\n" +
                        "\t\t\t          tag=\"visible,161_ФЗ\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"A8D5600F\"\n" +
                        "\t\t\t\t\t      path=\"logos/1635500829276258839\"\n" +
                        "\t\t\t\t\t      size=\"1676\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1337\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер договора\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${10}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Мест.связь\"\n" +
                        "\t\t\t          grpId=\"1078\"\n" +
                        "\t\t\t          id=\"31\"\n" +
                        "\t\t\t          inn=\"7710016640\"\n" +
                        "\t\t\t          jDocDate=\"22.10.2004\"\n" +
                        "\t\t\t          jDocNum=\"М-ДА-060904-ПАВ-01/13767\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ОАО &quot;Московская городская телефонная сеть&quot;\"\n" +
                        "\t\t\t          keywords=\"телефон|MGTC|гусев|mgts|МГТС|119454|7710016640\"\n" +
                        "\t\t\t          lName=\"ОАО &quot;Московская городская телефонная сеть&quot;\"\n" +
                        "\t\t\t          logo=\"logos/6595634883748806293\"\n" +
                        "\t\t\t          logo_crc=\"32211255\"\n" +
                        "\t\t\t          logo_size=\"2984\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Местная связь\"\n" +
                        "\t\t\t          sName=\"МГТС\"\n" +
                        "\t\t\t          supportPhone=\"+74956360636\"\n" +
                        "\t\t\t          tag=\"Not exist PDF_CANCELS,Аффилированные провайдеры МТС\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"32211255\"\n" +
                        "\t\t\t\t\t      path=\"logos/6595634883748806293\"\n" +
                        "\t\t\t\t\t      size=\"2984\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams>\n" +
                        "\t\t\t\t\t<param name=\"_receipt_\"\n" +
                        "\t\t\t\t\t       value=\"Платеж принят без акцептования МТС \\nПлатёж может быть зачислен на счёт с задержкой\"/>\n" +
                        "\t\t\t\t\t<param name=\"_extra_TYPE\"\n" +
                        "\t\t\t\t\t       value=\"1\"/>\n" +
                        "\t\t\t\t\t<param name=\"_extra_PAY_TYPE\"\n" +
                        "\t\t\t\t\t       value=\"ABON\"/>\n" +
                        "\t\t\t\t</constParams>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"5918\"\n" +
                        "\t\t\t\t\t      title=\" \">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Если Вы не являетесь пользователем домашнего телефона МГТС, введите 10-ти значный виртуальный номер, который указан в Едином счёте за услуги связи МГТС в поле Телефон.\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер домашнего телефона.\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${10}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"Сотовая связь МОТИВ\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Сотовая св.\"\n" +
                        "\t\t\t          grpId=\"20\"\n" +
                        "\t\t\t          id=\"36\"\n" +
                        "\t\t\t          inn=\"6661079603\"\n" +
                        "\t\t\t          jDocDate=\"29.08.2005\"\n" +
                        "\t\t\t          jDocNum=\"М-01/08-05\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Екатеринбург- 2000&quot;\"\n" +
                        "\t\t\t          keywords=\"мотив|motiv|матиф\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Екатеринбург- 2000&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/prv36_8bc0372.gif\"\n" +
                        "\t\t\t          logo_crc=\"8BC0372\"\n" +
                        "\t\t\t          logo_size=\"4413\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"10\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          receiptName=\"Сотовая связь\"\n" +
                        "\t\t\t          sName=\"Сотовая связь МОТИВ\"\n" +
                        "\t\t\t          supportPhone=\"+73432690000\"\n" +
                        "\t\t\t          tag=\"visible,ranges,norubl,161_ФЗ,sms_advert,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"8BC0372\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/prv36_8bc0372.gif\"\n" +
                        "\t\t\t\t\t      size=\"4413\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"654\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Номер телефона вводится без '8'\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"(&lt;!^\\\\d+${3}&gt;)&lt;!^\\\\d+${3}&gt;-&lt;!^\\\\d+${2}&gt;-&lt;!^\\\\d+${2}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"Мегафон. Сотовая связь\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Сотовая св.\"\n" +
                        "\t\t\t          grpId=\"20\"\n" +
                        "\t\t\t          id=\"40\"\n" +
                        "\t\t\t          inn=\"3664044522\"\n" +
                        "\t\t\t          jDocDate=\"25.05.2005\"\n" +
                        "\t\t\t          jDocNum=\"783\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ОАО &quot;МегаФон&quot; Центральный филиал \"\n" +
                        "\t\t\t          keywords=\"мега фон Ц|мегафон Ц|Мега Центр|mega fon|megafon\"\n" +
                        "\t\t\t          lName=\"ОАО &quot;МегаФон&quot; Центральный филиал \"\n" +
                        "\t\t\t          logo=\"logos/7611938521725880377\"\n" +
                        "\t\t\t          logo_crc=\"D4084634\"\n" +
                        "\t\t\t          logo_size=\"3563\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\".01\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Сотовая связь\"\n" +
                        "\t\t\t          sName=\"Мегафон\"\n" +
                        "\t\t\t          supportPhone=\"8-800-550-70-95, баланс: *100#\"\n" +
                        "\t\t\t          tag=\"visible,ranges,top8collapse,Аффилированные провайдеры Мегафон,sms_advert\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"D4084634\"\n" +
                        "\t\t\t\t\t      path=\"logos/7611938521725880377\"\n" +
                        "\t\t\t\t\t      size=\"3563\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams>\n" +
                        "\t\t\t\t\t<param name=\"_receipt_\"\n" +
                        "\t\t\t\t\t       value=\"Номер для операций с ошибочными платежами: 8 800 550 7095\"/>\n" +
                        "\t\t\t\t</constParams>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"656\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Номер телефона вводится без '8'\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"(&lt;!^\\\\d+${3}&gt;)&lt;!^\\\\d+${3}&gt;-&lt;!^\\\\d+${2}&gt;-&lt;!^\\\\d+${2}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"TELE2\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Сотовая св.\"\n" +
                        "\t\t\t          grpId=\"20\"\n" +
                        "\t\t\t          id=\"42\"\n" +
                        "\t\t\t          inn=\"6163025500\"\n" +
                        "\t\t\t          jDocDate=\"01.10.2005\"\n" +
                        "\t\t\t          jDocNum=\"310\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ЗАО &quot;Ростовская сотовая связь&quot;\"\n" +
                        "\t\t\t          keywords=\"tele 2|tele2|tele|теле два|теле2\"\n" +
                        "\t\t\t          lName=\"ЗАО &quot;Ростовская сотовая связь&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/prv42_9dec709.gif\"\n" +
                        "\t\t\t          logo_crc=\"9DEC709\"\n" +
                        "\t\t\t          logo_size=\"898\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\".01\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          receiptName=\"Сотовая связь\"\n" +
                        "\t\t\t          sName=\"TELE2\"\n" +
                        "\t\t\t          small_logo=\"logos/!anonymous/prv42_a734abf.gif\"\n" +
                        "\t\t\t          small_logo_crc=\"A734ABF\"\n" +
                        "\t\t\t          small_logo_size=\"1662\"\n" +
                        "\t\t\t          supportPhone=\"+79515200610\"\n" +
                        "\t\t\t          tag=\"visible,ranges,Tele2,norubl,sms_advert,Провайдеры Tele2,Неправильные провы\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"9DEC709\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/prv42_9dec709.gif\"\n" +
                        "\t\t\t\t\t      size=\"898\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t\t<logo crc=\"A734ABF\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/prv42_a734abf.gif\"\n" +
                        "\t\t\t\t\t      size=\"1662\"\n" +
                        "\t\t\t\t\t      type=\"top8\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"657\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Номер телефона вводится без '8'\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"(&lt;!^\\\\d+${3}&gt;)&lt;!^\\\\d+${3}&gt;-&lt;!^\\\\d+${2}&gt;-&lt;!^\\\\d+${2}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"ОНС24\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"52\"\n" +
                        "\t\t\t          inn=\"5024111340\"\n" +
                        "\t\t\t          jDocDate=\"01.10.2010\"\n" +
                        "\t\t\t          jDocNum=\"7412\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;ОНС&quot;\"\n" +
                        "\t\t\t          keywords=\"цилот бил|ОНС24|Zelot Bil|целот биллинг\"\n" +
                        "\t\t\t          lName=\"ООО &quot;ОНС&quot;\"\n" +
                        "\t\t\t          logo=\"logos/9069455850356490383\"\n" +
                        "\t\t\t          logo_crc=\"FBC5F4B\"\n" +
                        "\t\t\t          logo_size=\"18317\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"ОНС24\"\n" +
                        "\t\t\t          supportPhone=\"+74959887412\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"FBC5F4B\"\n" +
                        "\t\t\t\t\t      path=\"logos/9069455850356490383\"\n" +
                        "\t\t\t\t\t      size=\"18317\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1121\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер лицевого счёта\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${1,20}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{1,20}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"62\"\n" +
                        "\t\t\t          inn=\"7736550616\"\n" +
                        "\t\t\t          jDocDate=\"01.10.2011\"\n" +
                        "\t\t\t          jDocNum=\"14\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Кардтел&quot;\"\n" +
                        "\t\t\t          keywords=\"kardtel|кардтел|кардтэл\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Кардтел&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/cardtel.gif\"\n" +
                        "\t\t\t          logo_crc=\"9CD3741C\"\n" +
                        "\t\t\t          logo_size=\"3665\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"CardTel\"\n" +
                        "\t\t\t          supportPhone=\"+74956495181\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,visible,161_ФЗ\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"9CD3741C\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/cardtel.gif\"\n" +
                        "\t\t\t\t\t      size=\"3665\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"3136\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Номер телефона или лицевого счета\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${10,16}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{10,16}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Тест.пров-ы\"\n" +
                        "\t\t\t          grpId=\"64\"\n" +
                        "\t\t\t          id=\"82\"\n" +
                        "\t\t\t          inn=\"7729096511\"\n" +
                        "\t\t\t          jDocType=\"undefined\"\n" +
                        "\t\t\t          jName=\"ООО Издательство &quot;Деловой мир&quot;\"\n" +
                        "\t\t\t          lName=\"Тестовый\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/prv2069universal_icon.gif\"\n" +
                        "\t\t\t          logo_crc=\"2f7ffde3\"\n" +
                        "\t\t\t          logo_size=\"5937\"\n" +
                        "\t\t\t          maxSum=\"5\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          prvPage=\"embed_flash_turavia.html\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          receiptName=\"Тестовые провайдеры\"\n" +
                        "\t\t\t          sName=\"Тестовый\"\n" +
                        "\t\t\t          tag=\"ranges,charity,Провайдеры на этапе подключения\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"2f7ffde3\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/prv2069universal_icon.gif\"\n" +
                        "\t\t\t\t\t      size=\"5937\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams>\n" +
                        "\t\t\t\t\t<param name=\"EmbedParams\"\n" +
                        "\t\t\t\t\t       value=\"25518\"/>\n" +
                        "\t\t\t\t</constParams>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"105268\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\"account\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${1,7}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{1,7}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\" prv_payment_id\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${1,7}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"prv_payment_id\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{1,7}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"2КОМ\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"88\"\n" +
                        "\t\t\t          inn=\"5046042235\"\n" +
                        "\t\t\t          jDocDate=\"01.11.2011\"\n" +
                        "\t\t\t          jDocNum=\"3/А-11\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Компания 2Ком&quot;\"\n" +
                        "\t\t\t          keywords=\"2ком|2com|два ком|dva rom|2kom|2KOM|2kom\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Компания 2Ком&quot;\"\n" +
                        "\t\t\t          logo=\"logos/2002633962574007310\"\n" +
                        "\t\t\t          logo_crc=\"821BEFCC\"\n" +
                        "\t\t\t          logo_size=\"2670\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"2КОМ\"\n" +
                        "\t\t\t          supportPhone=\"+74957274233\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,161_ФЗ,no-qo,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"821BEFCC\"\n" +
                        "\t\t\t\t\t      path=\"logos/2002633962574007310\"\n" +
                        "\t\t\t\t\t      size=\"2670\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1101\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер договора\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${1,7}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{1,7}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider buttonName=\"Интернет\"\n" +
                        "\t\t\t          curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"610\"\n" +
                        "\t\t\t          id=\"90\"\n" +
                        "\t\t\t          inn=\"5052013497\"\n" +
                        "\t\t\t          jDocDate=\"13.10.2006\"\n" +
                        "\t\t\t          jDocNum=\"Ф-О-131006-КЕН-01\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Фрязинский городской информационный центр&quot;\"\n" +
                        "\t\t\t          keywords=\"Фрязено|Фрязино|Врязино\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Фрязинский городской информационный центр&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/prv90_2a17e54c.gif\"\n" +
                        "\t\t\t          logo_crc=\"2a17e54c\"\n" +
                        "\t\t\t          logo_size=\"4764\"\n" +
                        "\t\t\t          maxSum=\"10000\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"Fryazino.Net (Интернет)\"\n" +
                        "\t\t\t          supportPhone=\"+74962555499\"\n" +
                        "\t\t\t          tag=\"Not exist PDF_CANCELS,quasi-online,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"2a17e54c\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/prv90_2a17e54c.gif\"\n" +
                        "\t\t\t\t\t      size=\"4764\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1144\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"AL\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите Ваш логин\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^.*${1,17}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^[0-9a-zA-Z]{1,17}$\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Рег.доменов\"\n" +
                        "\t\t\t          grpId=\"114\"\n" +
                        "\t\t\t          id=\"92\"\n" +
                        "\t\t\t          inn=\"7705350575\"\n" +
                        "\t\t\t          jDocDate=\"13.02.2006\"\n" +
                        "\t\t\t          jDocNum=\"01/NIC\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"АНО &quot;Региональный Сетевой Информационный Центр&quot;\"\n" +
                        "\t\t\t          keywords=\"ru-center|Ру центр|rucentr|RUCENTER|руцентр\"\n" +
                        "\t\t\t          lName=\"АНО &quot;Региональный Сетевой Информационный Центр&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/rucenter.gif\"\n" +
                        "\t\t\t          logo_crc=\"BB6F5153\"\n" +
                        "\t\t\t          logo_size=\"2885\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Регистрация доменов\"\n" +
                        "\t\t\t          sName=\"RU CENTER\"\n" +
                        "\t\t\t          supportPhone=\"+74959944601\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,visible,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"BB6F5153\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/rucenter.gif\"\n" +
                        "\t\t\t\t\t      size=\"2885\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1152\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"Укажите номер и тип вашего договора. Кнопка Вперёд станет активной только после внесения всех данных.\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер договора\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${3,15}&gt;/NIC-&lt;!^[DREG]+${1,3}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{3,15}/NIC-[DREG]{1,3}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"ALC\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"112\"\n" +
                        "\t\t\t          inn=\"7604054555\"\n" +
                        "\t\t\t          jDocDate=\"05.05.2006\"\n" +
                        "\t\t\t          jDocNum=\"060505-ПЛ\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО  &quot;Нетис Телеком&quot;\"\n" +
                        "\t\t\t          keywords=\"netis|Нэтис|нитис\"\n" +
                        "\t\t\t          lName=\"ООО  &quot;Нетис Телеком&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/netis.gif\"\n" +
                        "\t\t\t          logo_crc=\"96BBBD77\"\n" +
                        "\t\t\t          logo_size=\"5105\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"НЕТИС\"\n" +
                        "\t\t\t          supportPhone=\"+74852646646\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"96BBBD77\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/netis.gif\"\n" +
                        "\t\t\t\t\t      size=\"5105\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1178\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер лицевого счёта\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${4,15}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{4,15}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"IP-телефон.\"\n" +
                        "\t\t\t          grpId=\"107\"\n" +
                        "\t\t\t          id=\"120\"\n" +
                        "\t\t\t          inn=\"7813509485\"\n" +
                        "\t\t\t          jDocDate=\"01.08.2015\"\n" +
                        "\t\t\t          jDocNum=\"АНГ-290615\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Аполло&quot;\"\n" +
                        "\t\t\t          keywords=\"Аполо|Аполлофон|Аполло|APOLLOPHONE\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Аполло&quot;\"\n" +
                        "\t\t\t          logo=\"logos/4402176900364061105\"\n" +
                        "\t\t\t          logo_crc=\"ECA2C90\"\n" +
                        "\t\t\t          logo_size=\"4853\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"IP-телефония\"\n" +
                        "\t\t\t          sName=\"APOLLOPHONE\"\n" +
                        "\t\t\t          supportPhone=\"+78126431020\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,visible,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"ECA2C90\"\n" +
                        "\t\t\t\t\t      path=\"logos/4402176900364061105\"\n" +
                        "\t\t\t\t\t      size=\"4853\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1102\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control footer=\"По зарегистрированному номеру телефона - 11 цифр в виде 7хххххххххх; по PIN-коду карты - 10 цифр; по номеру договора - 5 или 6 цифр.\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите номер телефона, PIN-код или номер договора\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${5,11}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{5,11}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Онлайн общ.\"\n" +
                        "\t\t\t          grpId=\"125\"\n" +
                        "\t\t\t          id=\"122\"\n" +
                        "\t\t\t          inn=\"7714548885\"\n" +
                        "\t\t\t          jDocDate=\"01.06.2008\"\n" +
                        "\t\t\t          jDocNum=\"8.06/12-П\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ЗАО &quot;Мамба&quot;\"\n" +
                        "\t\t\t          keywords=\"mamba|мамб|мам ба\"\n" +
                        "\t\t\t          lName=\"ЗАО &quot;Мамба&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/mamba.gif\"\n" +
                        "\t\t\t          logo_crc=\"4E12339E\"\n" +
                        "\t\t\t          logo_size=\"2353\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"38\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Онлайн общение\"\n" +
                        "\t\t\t          sName=\"Мамба\"\n" +
                        "\t\t\t          supportPhone=\"8(495) 787-58-22\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,visible,new_visibility_root_rus,new_keyboard,161_ФЗ\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"4E12339E\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/mamba.gif\"\n" +
                        "\t\t\t\t\t      size=\"2353\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"4038\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\" Введите e-mail или логин, на который зарегистрирована анкета:\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^.+${1,32}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^.{1,32}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"AL\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"128\"\n" +
                        "\t\t\t          inn=\"7701239135\"\n" +
                        "\t\t\t          jDocDate=\"01.05.2008\"\n" +
                        "\t\t\t          jDocNum=\"0508/ПС\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Интернет-Космос&quot;\"\n" +
                        "\t\t\t          keywords=\"спасенэт|спасенет|спасэнет|спасэнэт\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Интернет-Космос&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/ikosmos.gif\"\n" +
                        "\t\t\t          logo_crc=\"8D652489\"\n" +
                        "\t\t\t          logo_size=\"4726\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"SPACENET\"\n" +
                        "\t\t\t          supportPhone=\"+74956416400\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"8D652489\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/ikosmos.gif\"\n" +
                        "\t\t\t\t\t      size=\"4726\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"986\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите ваш персональный идентификатор (ПИН)\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${4,10}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{4,10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"135\"\n" +
                        "\t\t\t          inn=\"7718774772\"\n" +
                        "\t\t\t          jDocDate=\"01.05.2010\"\n" +
                        "\t\t\t          jDocNum=\"34/15\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Монтаж Телеком Сервис&quot;\"\n" +
                        "\t\t\t          keywords=\"мос лине|Mos Line|мослин\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Монтаж Телеком Сервис&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/mosline.gif\"\n" +
                        "\t\t\t          logo_crc=\"63BE227B\"\n" +
                        "\t\t\t          logo_size=\"2638\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"MosLine\"\n" +
                        "\t\t\t          supportPhone=\"+74959889595\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"63BE227B\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/mosline.gif\"\n" +
                        "\t\t\t\t\t      size=\"2638\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1196\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"AL\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите свой логин\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^.*${1,16}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^.{1,16}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"141\"\n" +
                        "\t\t\t          inn=\"7715255320\"\n" +
                        "\t\t\t          jDocDate=\"21.06.2006\"\n" +
                        "\t\t\t          jDocNum=\"1-ОСМП/2006\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Неторн&quot;\"\n" +
                        "\t\t\t          keywords=\"netorn|нэторн\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Неторн&quot;\"\n" +
                        "\t\t\t          logo=\"logos/8074169321670590786\"\n" +
                        "\t\t\t          logo_crc=\"44057525\"\n" +
                        "\t\t\t          logo_size=\"5408\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"Неторн / СТК\"\n" +
                        "\t\t\t          supportPhone=\"+74951119595\"\n" +
                        "\t\t\t          tag=\"quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"44057525\"\n" +
                        "\t\t\t\t\t      path=\"logos/8074169321670590786\"\n" +
                        "\t\t\t\t\t      size=\"5408\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"461\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер договора\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${7}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{7}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"143\"\n" +
                        "\t\t\t          inn=\"7735124622\"\n" +
                        "\t\t\t          jDocDate=\"07.07.2006\"\n" +
                        "\t\t\t          jDocNum=\"3-П\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"АНО &quot;МЖК-ТЕЛЕКОМ&quot;\"\n" +
                        "\t\t\t          keywords=\"mjknet|мжктелеком|мжк теле\"\n" +
                        "\t\t\t          lName=\"АНО &quot;МЖК-ТЕЛЕКОМ&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/mzktelecom.gif\"\n" +
                        "\t\t\t          logo_crc=\"8DDC2DC9\"\n" +
                        "\t\t\t          logo_size=\"2251\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"МЖК ТЕЛЕКОМ\"\n" +
                        "\t\t\t          supportPhone=\"+74995003616\"\n" +
                        "\t\t\t          tag=\"quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду,ННТУ\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"8DDC2DC9\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/mzktelecom.gif\"\n" +
                        "\t\t\t\t\t      size=\"2251\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"373\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите свой идентификационный номер\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${5,10}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{5,10}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          grpId=\"485\"\n" +
                        "\t\t\t          id=\"153\"\n" +
                        "\t\t\t          inn=\"3123011520\"\n" +
                        "\t\t\t          jDocDate=\"11.09.2012\"\n" +
                        "\t\t\t          jDocNum=\"БАМ-БА-05/2012\"\n" +
                        "\t\t\t          jDocType=\"bank\"\n" +
                        "\t\t\t          jName=\"КИВИ Банк (АО)\"\n" +
                        "\t\t\t          keywords=\"ГлобалСтрим|GlobalStrim|Стрим|Strim|Global|Глобал\"\n" +
                        "\t\t\t          lName=\"Глобал Стрим\"\n" +
                        "\t\t\t          logo=\"logos/6075889011965148333\"\n" +
                        "\t\t\t          logo_crc=\"A0CA420A\"\n" +
                        "\t\t\t          logo_size=\"1799\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          prvPage=\"modules/sinap/index.html\"\n" +
                        "\t\t\t          ratingId=\"1\"\n" +
                        "\t\t\t          sName=\"Глобал Стрим_test\"\n" +
                        "\t\t\t          supportPhone=\"8-800-775-40-13\"\n" +
                        "\t\t\t          tag=\"Провайдеры на этапе подключения\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"A0CA420A\"\n" +
                        "\t\t\t\t\t      path=\"logos/6075889011965148333\"\n" +
                        "\t\t\t\t\t      size=\"1799\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"132236\"\n" +
                        "\t\t\t\t\t      title=\"бла-бла-бла\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control disp_desc=\"Где-то\"\n" +
                        "\t\t\t\t\t\t\t         disp_title=\"Ой!\"\n" +
                        "\t\t\t\t\t\t\t         disp_type=\"receipt\"\n" +
                        "\t\t\t\t\t\t\t         header=\"Введите свой логин или нет\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^.*${1,20}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"true\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^.{1,20}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"true\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"AL\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"157\"\n" +
                        "\t\t\t          inn=\"3442052988\"\n" +
                        "\t\t\t          jDocDate=\"09.07.2008\"\n" +
                        "\t\t\t          jDocNum=\"25\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Вист-он-лайн&quot;\"\n" +
                        "\t\t\t          keywords=\"вистонлайн|вист онлайн|vistcom|vistonlain\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Вист-он-лайн&quot;\"\n" +
                        "\t\t\t          logo=\"logos/4218910737118312589\"\n" +
                        "\t\t\t          logo_crc=\"E58AA802\"\n" +
                        "\t\t\t          logo_size=\"904\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"Вист он Лайн\"\n" +
                        "\t\t\t          supportPhone=\"90-34-92\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду,ННТУ\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"E58AA802\"\n" +
                        "\t\t\t\t\t      path=\"logos/4218910737118312589\"\n" +
                        "\t\t\t\t\t      size=\"904\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1198\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер счёта\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${4,15}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{4,15}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Интернет\"\n" +
                        "\t\t\t          grpId=\"103\"\n" +
                        "\t\t\t          id=\"161\"\n" +
                        "\t\t\t          inn=\"7716196741\"\n" +
                        "\t\t\t          jDocDate=\"01.07.2006\"\n" +
                        "\t\t\t          jDocNum=\"1\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ООО &quot;Геликон - Эппл&quot;\"\n" +
                        "\t\t\t          keywords=\"gelicon|гиликон|гелекон|геликон\"\n" +
                        "\t\t\t          lName=\"ООО &quot;Геликон - Эппл&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/prv161_9c64db69.GIF\"\n" +
                        "\t\t\t          logo_crc=\"9c64db69\"\n" +
                        "\t\t\t          logo_size=\"1320\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"1\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Интернет\"\n" +
                        "\t\t\t          sName=\"Геликон\"\n" +
                        "\t\t\t          supportPhone=\"+74957871199\"\n" +
                        "\t\t\t          tag=\"quasi-online,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду,ННТУ\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"9c64db69\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/prv161_9c64db69.GIF\"\n" +
                        "\t\t\t\t\t      size=\"1320\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1199\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"AL\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите логин\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^.*${1,16}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^.{1,16}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t\t<provider curId=\"643\"\n" +
                        "\t\t\t          fiscalName=\"Рег.доменов\"\n" +
                        "\t\t\t          grpId=\"114\"\n" +
                        "\t\t\t          id=\"167\"\n" +
                        "\t\t\t          inn=\"7720589079\"\n" +
                        "\t\t\t          jDocDate=\"01.04.2008\"\n" +
                        "\t\t\t          jDocNum=\"005А\"\n" +
                        "\t\t\t          jDocType=\"qiwi\"\n" +
                        "\t\t\t          jName=\"ЗАО &quot;1 Гб.ру&quot;\"\n" +
                        "\t\t\t          keywords=\"1гбру|1 гб ру|одингбру\"\n" +
                        "\t\t\t          lName=\"ЗАО &quot;1 Гб.ру&quot;\"\n" +
                        "\t\t\t          logo=\"logos/!anonymous/insolve.gif\"\n" +
                        "\t\t\t          logo_crc=\"F4A41335\"\n" +
                        "\t\t\t          logo_size=\"2847\"\n" +
                        "\t\t\t          maxSum=\"15000\"\n" +
                        "\t\t\t          minSum=\"10\"\n" +
                        "\t\t\t          ratingId=\"0\"\n" +
                        "\t\t\t          receiptName=\"Регистрация доменов\"\n" +
                        "\t\t\t          sName=\"1 Gb.ru\"\n" +
                        "\t\t\t          supportPhone=\"+78005551540\"\n" +
                        "\t\t\t          tag=\"СТАРЫЕ_АКТЫ_161_ФЗ,hideInTop8,quasi-online,visible,new_visibility_root_rus,161_ФЗ,Провайдер выведен в Рапиду\">\n" +
                        "\t\t\t\t<logos>\n" +
                        "\t\t\t\t\t<logo crc=\"F4A41335\"\n" +
                        "\t\t\t\t\t      path=\"logos/!anonymous/insolve.gif\"\n" +
                        "\t\t\t\t\t      size=\"2847\"\n" +
                        "\t\t\t\t\t      type=\"standard\"/>\n" +
                        "\t\t\t\t</logos>\n" +
                        "\t\t\t\t<constParams/>\n" +
                        "\t\t\t\t<pages>\n" +
                        "\t\t\t\t\t<page orderId=\"1\"\n" +
                        "\t\t\t\t\t      pageId=\"1201\"\n" +
                        "\t\t\t\t\t      useOnline=\"true\">\n" +
                        "\t\t\t\t\t\t<controls>\n" +
                        "\t\t\t\t\t\t\t<control layout=\"DG\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"2\"\n" +
                        "\t\t\t\t\t\t\t         type=\"keyboard\"/>\n" +
                        "\t\t\t\t\t\t\t<control header=\"Введите номер лицевого счёта\"\n" +
                        "\t\t\t\t\t\t\t         mask=\"&lt;!^\\\\d+${4,15}&gt;\"\n" +
                        "\t\t\t\t\t\t\t         name=\"account\"\n" +
                        "\t\t\t\t\t\t\t         nobr=\"false\"\n" +
                        "\t\t\t\t\t\t\t         orderId=\"1\"\n" +
                        "\t\t\t\t\t\t\t         regexp=\"^\\\\d{4,15}$\"\n" +
                        "\t\t\t\t\t\t\t         strip=\"false\"\n" +
                        "\t\t\t\t\t\t\t         type=\"text_input\"/>\n" +
                        "\t\t\t\t\t\t</controls>\n" +
                        "\t\t\t\t\t</page>\n" +
                        "\t\t\t\t</pages>\n" +
                        "\t\t\t</provider>\n" +
                        "\t\t</getUIProviders>\n" +
                        "\t</providers>\n" +
                        "</response>\n"
        };
        Random random = new Random();
        int index = random.nextInt(listOfResponses.length);

        return listOfResponses[index];
    }
}
