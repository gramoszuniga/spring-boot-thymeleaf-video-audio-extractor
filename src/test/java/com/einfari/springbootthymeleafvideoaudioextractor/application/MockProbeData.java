package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.github.kokorin.jaffree.LogCategory;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.Rational;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.data.ProbeData;
import com.github.kokorin.jaffree.ffprobe.data.ProbeDataConverter;
import com.github.kokorin.jaffree.ffprobe.data.ValueConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-15
 **/
public class MockProbeData implements ProbeData {

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public <T> T getValue(String s, ValueConverter<T> valueConverter) {
        return null;
    }

    @Override
    public String getString(String s) {
        return "codecName";
    }

    @Override
    public Boolean getBoolean(String s) {
        return null;
    }

    @Override
    public Long getLong(String s) {
        return null;
    }

    @Override
    public Integer getInteger(String s) {
        return 1;
    }

    @Override
    public Float getFloat(String s) {
        return null;
    }

    @Override
    public Double getDouble(String s) {
        return null;
    }

    @Override
    public StreamType getStreamType(String s) {
        return null;
    }

    @Override
    public LogLevel getLogLevel(String s) {
        return null;
    }

    @Override
    public LogCategory getLogCategory(String s) {
        return null;
    }

    @Override
    public Rational getRational(String s) {
        return null;
    }

    @Override
    public Rational getRatio(String s) {
        return null;
    }

    @Override
    public List<ProbeData> getSubDataList(String s) {
        return null;
    }

    @Override
    public <T> List<T> getSubDataList(String s, ProbeDataConverter<T> probeDataConverter) {
        return new ArrayList<>();
    }

    @Override
    public ProbeData getSubData(String s) {
        return null;
    }

    @Override
    public <T> T getSubData(String s, ProbeDataConverter<T> probeDataConverter) {
        return null;
    }

    @Override
    public Object getSubDataValue(String s, String s1) {
        return null;
    }

    @Override
    public <T> T getSubDataValue(String s, String s1, ValueConverter<T> valueConverter) {
        return null;
    }

    @Override
    public String getSubDataString(String s, String s1) {
        return null;
    }

    @Override
    public Long getSubDataLong(String s, String s1) {
        return null;
    }

    @Override
    public Integer getSubDataInteger(String s, String s1) {
        return null;
    }

    @Override
    public Double getSubDataDouble(String s, String s1) {
        return null;
    }

    @Override
    public Float getSubDataFloat(String s, String s1) {
        return null;
    }

}