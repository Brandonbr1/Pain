#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;



vec3 CEToneMapping(vec3 color, float adapted_lum)
{
    return 1 - exp(-adapted_lum * color);
}

vec3 ACESToneMapping(vec3 color, float adapted_lum) {
	const float A = 2.51f;
	const float B = 0.03f;
	const float C = 2.43f;
	const float D = 0.59f;
	const float E = 0.14f;
	color *= adapted_lum;
	return (color * (A * color + B)) / (color * (C * color + D) + E);
}

vec3 filmic(vec3 x) {
  vec3 X = max(vec3(0.0), x - 0.004);
  vec3 result = (X * (8.8 * X + 0.8)) / (X * (6.8 * X + 2.0) + 0.17);
  return pow(result, vec3(2.2));
}


void main(){

    vec4 Color = texture2D (DiffuseSampler, texCoord);

    Color.rgb = CEToneMapping(Color.rgb, 1);

     gl_FragColor = vec4(Color.rgb, Color.a);
}
