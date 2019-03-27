package me.herrlestrate.kadushko_artyom_info;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.metrica.push.YandexMetricaPush;

public class Application extends android.app.Application {

    private static String API_key = "db6c6d68-80e6-41dc-9e52-bf76e3e59348";
    //private static String API_key = "";

    @Override
    public void onCreate(){
        super.onCreate();
        // Создание расширенной конфигурации библиотеки.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(API_key).build();
        // Инициализация AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetricaPush.init(getApplicationContext());
        YandexMetrica.reportEvent("Application started!");
    }
}
