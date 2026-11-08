#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;



vec3 CEToneMapping(vec3 color, float adapted_lum)
{
    return 1 - exp(-adapted_lum * color);
}


void main(){

    vec4 Color = texture2D (DiffuseSampler, texCoord);

    Color.rgb = CEToneMapping(Color.rgb, 1);

     gl_FragColor = vec4(Color.rgb, Color.a);
}
