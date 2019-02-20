package com.kstarrain.utils.callback;

public interface PacketCallback<R, P> {

    R execute(P singleUnit);

}
