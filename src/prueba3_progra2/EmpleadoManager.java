/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba3_progra2;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
public class EmpleadoManager {
    private RandomAccessFile rcods,remps;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public EmpleadoManager(){
        try{
            // 1. asegurar que el folder de Company Exista
            File mf = new File("Company");
            mf.mkdir();
            // 2. Instanciar los RAFs dentro del folder company
            rcods=new RandomAccessFile("company/codigo.emp","rw");
            remps=new RandomAccessFile("company/empleado.emp","rw");
            // 3. Inicializar el archivo de codigo,si, es nuevo.
            initCodes();
        }catch(IOException e){
            System.out.println("Error");
        }
    
    }
    
    private void initCodes() throws IOException{
    //Cotejar el tamaño del archivo
     if(rcods.length()==0)
            rcods.writeInt(1);
    }
    
    private int getCode()throws IOException{
    // leer el archivo
    // puntero
        rcods.seek(0);
        int codigo=rcods.readInt();
        rcods.seek(0);
        rcods.writeInt(codigo+1); 
        return codigo;
    }
    
    /*
    formato Empleado.emp
    
    int code
    String name
    double salary
    long fechaContratacion
    long fechaDespido
    */
    
    public void addEmployee(String name, double monto)throws IOException{
        remps.seek(remps.length());
        int code=getCode();
        remps.writeInt(code);
        //Nombre
        remps.writeUTF(name);
        //Salario
        remps.writeDouble(monto);
        //Fecha de Contratación
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        //Fecha de Despido
        remps.writeLong(0);
        //Crear carpeta y archivo individual de cada empreado
        createEmployeeFolders(code);
    }
    
    private String employeeFolder(int code){
        return "company/empleado"+code;
        
    }
    
    private void createEmployeeFolders(int code)throws IOException{
        File edir=new File(employeeFolder(code));
        edir.mkdir();
        //Crear archivo del empleado
        this.createYearSalesFile(code);
    }
    
    private RandomAccessFile salesFileFor(int code)throws IOException{
        String dirPadre=employeeFolder(code);
        int yearActual= Calendar.getInstance().get(Calendar.YEAR);
        String path=dirPadre+"/ventas"+yearActual+".emp";
        
        return new RandomAccessFile(path,"rw");
    }
    
    private void createYearSalesFile(int code)throws IOException{
        RandomAccessFile raf = salesFileFor(code);
        if(raf.length()==0){
            for(int mes =0;mes<12;mes++){
                raf.writeDouble(0);
                raf.writeBoolean(false);
            }
        }
    }
    
    
    public void empleadosNoDesp() throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int codigo = remps.readInt();
            String nombre = remps.readUTF();
            double salario = remps.readDouble();
            long fechaContratacion = remps.readLong();
            long fechaDespido = remps.readLong();

            if (fechaDespido == 0) {
                Date date = new Date(fechaContratacion);
                System.out.printf(codigo+" - "+nombre+" - "+salario+" - "+dateFormat.format(date));
            }
        }
        
    }
    
    public boolean empleadoActivo(int code)throws IOException{
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            long position = remps.getFilePointer();
            int codigoActual = remps.readInt();
            remps.readUTF();
            remps.readDouble();
            remps.readLong();
            long fechaDespido = remps.readLong();

            if (codigoActual == code && fechaDespido == 0) {
                remps.seek(position);
                return true;
            }
        }
        return false;
    }
    
    public boolean despedirEmpleado(int code)throws IOException{
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int codigo = remps.readInt();
            String nombre = remps.readUTF();
            remps.readDouble();
            remps.readLong();
            long fechaDespido = remps.readLong();

            if (codigo == code && fechaDespido == 0) {
                remps.seek(remps.getFilePointer() - 8);
                remps.writeLong(Calendar.getInstance().getTimeInMillis());
                System.out.println("Empleado despedido: " + nombre);
                return true;
            }
        }
        return false;
    }
}
