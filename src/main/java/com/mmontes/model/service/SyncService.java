package com.mmontes.model.service;

import com.mmontes.util.Constants;
import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.dto.TIPSyncDto;
import com.mmontes.util.dto.xml.TIPXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service("SyncService")
@EnableScheduling
public class SyncService {

    private final int MAX_TRIES = 3;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ConfigService configService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TIPService tipService;

    @Autowired
    private DtoService dtoService;

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
                    System.out.println("Pausada ejecución durante " + (i * 3 + 1) + "segundos");
                } catch (InterruptedException e1) {
                }
                i++;
                if (i == MAX_TRIES) {
                    System.out.println("La siguiente URL no se ha podido procesar: " + url);
                }
                System.out.println("----------------------------");
            }
        }
        return new ArrayList<>();
    }

    //@Scheduled(cron = Constants.CRON_SYNC_TIPS)
    public void syncTIPs() {
        String url;
        List<TIPSyncDto> tips = new ArrayList<>();
        List<OSMTypeDto> osmTypeDtos = configService.getOSMtypes(true);
        List<CityEnvelopeDto> cityEnvelopeDtos = cityService.getCityEnvelopes();

        for (OSMTypeDto osmType : osmTypeDtos) {
            for (CityEnvelopeDto cityEnvelope : cityEnvelopeDtos) {
                url = String.format(Constants.OSM_BASE_URL, cityEnvelope.getGeom(), osmType.getKey(), osmType.getValue());
                System.out.println("OSM get:" + url);
                List<TIPSyncDto> tipsSync = doRequest(url, osmType.getTipTypeID());
                if (tipsSync != null) tips.addAll(tipsSync);
            }
        }
        if (!tips.isEmpty()) {
            tipService.syncTIPs(tips);
        }
        System.out.println("****************** FIN SINCRONIZACIÓN *******************");

    }

    //@Scheduled(cron = Constants.CRON_SYNC_ADDRESSES)
    public void syncAddresses() {
        System.out.println("****************** COMIENZO SINCRONIZACIÓN DIRECCIONES *******************");
        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        tipService.populateAddresses();

        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        System.out.println("****************** FIN SINCRONIZACIÓN DIRECCIONES *******************");
    }


}
