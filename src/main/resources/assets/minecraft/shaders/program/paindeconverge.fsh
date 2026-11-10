#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;

const float DivergeX = 8.0;

void main() {

    float RedValue   = texture2D(DiffuseSampler, texCoord + vec2(DivergeX * -oneTexel.x, 0.0)).r;
    float GreenValue = texture2D(DiffuseSampler, texCoord + vec2(0.0, 0.0)).g;
    float BlueValue  = texture2D(DiffuseSampler, texCoord + vec2(DivergeX *  oneTexel.x, 0.0)).b;
    float AlphaValue  = texture2D(DiffuseSampler, texCoord).a;

    gl_FragColor = vec4(RedValue, GreenValue, BlueValue, AlphaValue);
}