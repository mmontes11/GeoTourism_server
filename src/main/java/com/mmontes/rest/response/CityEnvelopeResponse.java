package com.mmontes.rest.response;

import com.mmontes.util.dto.CityEnvelopeDto;

import java.util.List;

public class CityEnvelopeResponse {

    private List<CityEnvelopeDto> cities;

    public CityEnvelopeResponse() {
    }

    public CityEnvelopeResponse(List<CityEnvelopeDto> cities) {
        this.cities = cities;
    }

    public List<CityEnvelopeDto> getCities() {
        return cities;
    }

    public void setCities(List<CityEnvelopeDto> cities) {
        this.cities = cities;
    }
}
