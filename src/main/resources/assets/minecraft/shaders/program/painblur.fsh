#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
const float Quality = 1;

void main(){

// https://xorshaders.weebly.com/tutorials/blur-shaders-5-part-1

vec4 Color;
    float v;
    // perhaps adjust more?
    for( float i=0.0;i<0.30;i+=0.15/Quality )
    {
            v = 0.9+i*0.1;//convert "i" to the 0.9 to 1 range
            Color += texture2D( DiffuseSampler, texCoord*v+0.5-0.5*v);
    }
    Color /= Quality;


gl_FragColor = vec4(Color.rgb, Color.a);


}