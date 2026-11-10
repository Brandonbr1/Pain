#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
const float vignette_strength = 0.78;


void main(){
    vec4 Color = texture2D(DiffuseSampler, texCoord);


     vec3 col = vec3(Color.r, Color.g, Color.b);

    float dist = distance(texCoord, vec2(0.5));
        float vignette = smoothstep(0.6, 0.9, dist);
        col *= mix(1.0, 1.0 - vignette, vignette_strength);



    gl_FragColor = vec4(col.rgb, col.a);
}