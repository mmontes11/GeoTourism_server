package com.mmontes.model.service;

import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.dto.TIPSyncDto;
import com.mmontes.util.dto.xml.TIPXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Carlos on 10/07/2016.
 */
@Service("SyncService")
@EnableScheduling
public class SyncService {

    private final int MAX_TRIES = 3;

    // private String server = "http://www.overpass-api.de/api/xapi_meta?node[bbox=%s][%s=%s]";
    private String server = "http://api.openstreetmap.fr/xapi?node[bbox=%s][%s=%s]";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ConfigService configService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TIPService tipService;

    @Autowired
    private DtoService dtoService;

    @Scheduled(cron = "0 8 * * * *") // A las 8 de la mañana (10 hora local) todos los días
    public void syncAddresses() {
        System.out.println("****************** COMIENZO SINCRONIZACIÓN DIRECCIONES *******************");
        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        tipService.populateAddresses();

        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        System.out.println("****************** FIN SINCRONIZACIÓN DIRECCIONES *******************");
    }


    // Se lanza automáticamente
    // TODO: Una vez se compruebe que funcione habrá que configurarlo para que se lance una vez a la semana (o al mes).
    // @Scheduled(cron = "0 2 * * *")
    public void sync() {

        String url;
        List<TIPSyncDto> tips = new ArrayList<>();
        List<OSMTypeDto> osmTypeDtos = configService.getOSMtypes(true);
        List<CityEnvelopeDto> cityEnvelopeDtos = cityService.getCityEnvelopes();

        int i;
        int j = 0;
        System.out.println("****************** COMIENZO SINCRONIZACIÓN *******************");
        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        System.out.println("Ejecuciones previstas: " + (osmTypeDtos.size() * cityEnvelopeDtos.size()));
        for (OSMTypeDto osmType : osmTypeDtos) {
            i = 0;
            for (CityEnvelopeDto cityEnvelope : cityEnvelopeDtos) {
                url = String.format(server, cityEnvelope.getGeom(), osmType.getKey(), osmType.getValue());
                System.out.println("OSM get:" + url);
                List<TIPSyncDto> tipsSync = doRequest(url, osmType.getTipTypeID());
                if (tipsSync != null) tips.addAll(tipsSync);

                // TODO: Breaks para debuggear
//                i++;
//                if (i == 4) break;
            }
//            j++;
//            if (j == 4) break;
        }
        if (!tips.isEmpty()) {
            System.out.println("Nuevos tips: " + tips.size());
            tipService.syncTIPs(tips);
        }
        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        System.out.println("****************** FIN SINCRONIZACIÓN *******************");

    }

    private List<TIPSyncDto> doRequest(String url, Long tipTypeId) {
        int i = 0;
        while (i < MAX_TRIES) {
            try {
                ResponseEntity<TIPXml> responseEntity = restTemplate.getForEntity(url, TIPXml.class);
                return dtoService.TIPXml2ListTIPSyncDto(responseEntity.getBody(), tipTypeId);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep((i * 2 + 1) * 1000);
                    System.out.println("Pausada ejecución durante " + (i * 3 + 1) + " segundos");
                } catch (InterruptedException e1) {
                }
                i++;
                if (i == MAX_TRIES) {
                    System.out.println("La siguiente URL NO se ha podido procesar: " + url);
                }
                System.out.println("----------------------------");
            }
        }
        return new ArrayList<>();
    }

}
