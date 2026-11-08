#version 120

// https://github.com/EntokitoDezonze/E-LITE-shaders

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

    float couter = 90;

  float noise(vec2 uv) {
        return fract(sin(dot(uv, vec2(12.9898, 78.233))) * mod(couter, 24.0) * 240.0);
    }

    vec3 filmGrain(vec3 color, float grainIntensity, vec2 uv) {
        float grain = noise(uv * 10.0);
        grain = (grain - 0.5) * 2.0;
        return color + grain * grainIntensity;
    }


void main(){
    vec4 Color = texture2D(DiffuseSampler, texCoord);

    Color.rgb = filmGrain(Color.rgb, 0.015, texCoord);

    gl_FragColor = vec4(Color.rgb, Color.a); //texture2D(Color.rgb, 0.1);
}
