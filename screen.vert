varying vec4 vertColor;
int i = 0;
void main(){
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
    vertColor = vec4(1, 0, 0, .75);
}