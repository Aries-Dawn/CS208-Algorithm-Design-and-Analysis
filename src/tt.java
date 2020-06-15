public class tt {
    public static void main(String[] args) {

        var target = "switclk, switrst, switchread, switchcs,switchaddr, switchrdata, switch_i";
        target = target.replaceAll(" ","");
        target = target.replaceAll("\n","");
        var name = target.split(",");
        var sb = new StringBuilder();
        for (var temp : name){
            sb.append(".").append(temp).append("(").append(temp).append("),\n");
        }
        System.out.println(sb.toString());
    }
}
