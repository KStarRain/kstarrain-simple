package com.kstarrain.utils;

public interface DataPacketCallback<R, P> {

    R execute(P singleUnit);

}
