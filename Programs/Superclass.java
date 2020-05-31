package viduoverriding;
 
public class Superclass {
    public int val;
    public  int temperature;
    public Superclass()
    {
        val = 0;
        temperature = 0;
    }
    public int check(int v)
    {
        int temperature
        if( v==0 ) {
                temperature = temperature + 5;
            }
        else
            temperature = temperature + v;

//       int p = v++;
//        if(v>=2 || v <=-2)
//        {
//            this.val = v;
//            return 1;
//        }
//        else
//        {
//            this.val = -v;
//            return -1;
//        }
    }
    public void hienThi() {
        System.out.println("Đây là phương thức hiển thị của lớp cha Superclass.");
    }

    public static void main(String[] args) {
        this.check(1);
    }
}