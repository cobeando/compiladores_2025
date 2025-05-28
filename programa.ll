;Programa: Prueba
source_filename = "Prueba.txt"
declare i32 @printf(i8*, ...)
declare float @llvm.fabs.f32(float)

@.integer = private constant [4 x i8] c"%d\0A\00"
@.float = private constant [4 x i8] c"%f\0A\00"
@.duple1 = private constant [6 x i8] c"(%f, \00"
@.duple2 = private constant [5 x i8] c"%f)\0A\00"
declare i32 @scanf(i8* %0, ...)
@int_read_format = unnamed_addr constant [3 x i8] c"%d\00"
@double_read_format = unnamed_addr constant [4 x i8] c"%d\0A\00"
%struct.Tuple = type { float, float }

@gb.128 = private constant [3 x i8] c"A\0A\00"
@gb.129 = private constant [27 x i8] c"El valor mas cercano es: \0A\00"
@gb.130 = private constant [49 x i8] c"La lista de números está vacía, resultado: 0\0A\00"
@gb.131 = global float 0.000000e+00
@gb.132 = global i32 0
define i32 @main(i32, i8**) {
	%ptro.133 = call i32 (i8*, ...) @printf(i8* getelementptr ([3 x i8], [3 x i8]* @gb.128, i64 0, i64 0))
%ptro.134 = call i32 (i8*, ...) @printf(i8* getelementptr ([3 x i8], [3 x i8]* @gb.128, i64 0, i64 0))
		ret i32 0
}


