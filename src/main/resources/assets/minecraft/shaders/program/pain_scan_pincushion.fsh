#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;

const vec4 Zero = vec4(0.0);
const vec4 Half = vec4(0.5);
const vec4 One = vec4(1.0);
const vec4 Two = vec4(2.0);

const float Pi = 3.1415926535;
const float PincushionAmount = 0.02;
const float CurvatureAmount = 0.02;
const float ScanlineAmount = 0.8;
const float ScanlineScale = 1.0;
const float ScanlineHeight = 1.0;
const float ScanlineBrightScale = 1.0;
const float ScanlineBrightOffset = 0.0;
const float ScanlineOffset = 0.0;
const vec3 Floor = vec3(0.05, 0.05, 0.05);
const vec3 Power = vec3(1.0, 1.0, 1.0);

vec3 reinhard(vec3 x) {
  return x / (0.5 + x);
}

vec3 reinhard1(vec3 x) {
  return x / (1.0 + x);
}

vec3 reinhardJodie05(vec3 x) {
  const vec3 lw = vec3(0.2126, 0.7152, 0.0722);

  float l = dot(x, lw);
  vec3 tv = x / (0.5 + x);

  return mix(x / (0.5 + l), tv, tv);
}

void main() {
    vec4 InTexel = texture2D(DiffuseSampler, texCoord);


    vec2 ScanCoord = texCoord;

    // -- Scanline Simulation --
    float InnerSine = ScanCoord.y * InSize.y * ScanlineScale * 0.25;
    float ScanBrightMod = sin(InnerSine * Pi + ScanlineOffset * InSize.y * 0.25);
    float ScanBrightness = mix(1.0, (pow(ScanBrightMod * ScanBrightMod, ScanlineHeight) * ScanlineBrightScale + 1.0) * 0.5, ScanlineAmount);
    vec3 ScanlineTexel = InTexel.rgb * ScanBrightness;
    
    // -- Color Compression (increasing the floor of the signal without affecting the ceiling) --
    ScanlineTexel = Floor + (One.xyz - Floor) * ScanlineTexel;
    
    ScanlineTexel.rgb = reinhardJodie05(ScanlineTexel.rgb); //pow(ScanlineTexel.rgb, Power);

    gl_FragColor = vec4(ScanlineTexel.rgb, InTexel.a);
}
