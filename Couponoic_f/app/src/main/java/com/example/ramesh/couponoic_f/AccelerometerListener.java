package com.example.ramesh.couponoic_f;
public interface AccelerometerListener{
    public void onShake(float force);
    public void onAccelerationChanged(float x, float y, float z);
}