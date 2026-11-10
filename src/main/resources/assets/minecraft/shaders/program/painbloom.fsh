#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

void main(){

    vec4 Color = texture2D (DiffuseSampler, texCoord);



     gl_FragColor = vec4(Color.rgb, Color.a);
}
