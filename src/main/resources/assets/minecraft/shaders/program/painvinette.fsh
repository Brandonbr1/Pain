#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
const float vignette_strength = 0.78;


void main(){
    vec4 Color = texture2D(DiffuseSampler, texCoord);


     vec4 col = vec4(Color.r, Color.g, Color.b, Color.a);

    float dist = distance(texCoord, vec2(0.5)); // this returns the center
     float vignette = smoothstep(0.3, 0.6, dist);
     col *= mix(1.0, 1.0 - clamp(vignette * 1.07, 0.0, 1.0), vignette_strength);



    gl_FragColor = vec4(col.rgb, col.a);
}