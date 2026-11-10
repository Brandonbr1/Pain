#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

const int Quality = 1;

void main(){

    vec2 texCoordCentered = texCoord - 0.5; // Move texcoord into the range [-0.5, +0.5]
    vec4 Color = vec4(0.0);
    int iterations = Quality * 2; // Do 2 iterations per quality level

    for (int i = 0; i < iterations; i++)
    {
        // Shrink texcoord each iteration to radially blur the image
        float v = 1.0 - (0.01 * float(i) / float(iterations));

        // Multiply by v, and add 0.5 to move texCoord back into the [0.0, 1.0] range
        Color += texture2D(DiffuseSampler, texCoordCentered * v + 0.5);
    }
    Color /= float(iterations);

    gl_FragColor = Color;

}
