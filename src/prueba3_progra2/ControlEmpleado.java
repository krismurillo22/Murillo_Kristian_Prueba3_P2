/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba3_progra2;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ControlEmpleado {
    
    public static void main (String[] args){
        int opcion=0;
        EmpleadoManager empleado = new EmpleadoManager();
        Scanner lea=new Scanner(System.in);
        lea.useDelimiter("\n");
        do{
            System.out.println("\n**** MENU ****\n");
            System.out.println("1- Agregar Empleado");
            System.out.println("2- Listar Empleado No Despedidos");
            System.out.println("3- Despedir Empleado");
            System.out.println("4- Buscar Empleado Activo");
            System.out.println("5- Salir");

            try{
                opcion=lea.nextInt();
                switch(opcion){
                    case 1:
                        System.out.print("Ingrese el nombre del empleado: ");
                        String nombre = lea.next();
                        System.out.print("Ingrese el salario del empleado: ");
                        double salario = lea.nextDouble();
                        empleado.addEmployee(nombre, salario);
                        System.out.println("Se agrego el empleado.");
                        break;
                    case 2:
                        System.out.println("**** LISTA DE EMPLEADOS ****");
                        empleado.empleadosNoDesp();                        
                        break;
                    case 3:
                        System.out.println("Ingrese el codigo del empleado que desea despedir:");
                        int codigodes=lea.nextInt();
                        if(empleado.despedirEmpleado(codigodes)){
                            System.out.println("El empleado fue despedido");
                        }else {
                            System.out.println("El empleado no se pudo despedir.");
                        }
                        break;
                    case 4:
                        System.out.println("Ingrese el codigo del empleado que desea revisar:");
                        int codigo=lea.nextInt();
                        if(empleado.empleadoActivo(codigo)){
                            System.out.println("El empleado esta activo");
                        }else if(!empleado.empleadoActivo(codigo)){
                            System.out.println("El empleado fue despedido.");
                        }
                        break;
                }
                
            }catch(InputMismatchException e){
                lea.nextLine();
                System.out.println("Por favor ingrese una opcion correcta");
            }catch(IOException e){
                e.printStackTrace();
            }
             
        }while(opcion!=5);
                      
    }
}
