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

vec3 reinhard(vec3 x) {
  return x / (0.5 + x);
}

vec3 reinhard1(vec3 x) {
  return x / (1.0 + x);
}

// less performance, but looks better
vec3 reinhardJodie(vec3 x) {
  const vec3 lw = vec3(0.2126, 0.7152, 0.0722);

  float l = dot(x, lw);
  vec3 tv = x / (1.0 + x);

  return mix(x / (1.0 + l), tv, tv);
}

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

    vec4 Color = texture2D (DiffuseSampler, texCoord);

     Color.rgb = reinhard1(Color.rgb);

                                        // 0.015
     Color.rgb = filmGrain(Color.rgb, 0.015, texCoord);

     gl_FragColor = vec4(Color.rgb, Color.a);
}
