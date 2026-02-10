class DrawScale{

    public void drawOneTick(int tickLength){
        drawOneTick(tickLength,-1);
    }

    public void drawOneTick(int tickLength, int tickLabel){
        for(int i = 0; i < tickLength; i++){
            System.out.print("-");
        }
        if(tickLabel >= 0 ){
            System.out.print(" " + tickLabel);
            
        }
        System.out.print("\n");
    }   

    public void drawTicks(int tickLength){
        if(tickLength > 0){
            drawTicks(tickLength - 1);
            drawOneTick(tickLength);
            drawTicks(tickLength - 1);
        }
    }


    public void drawRuler(int nInches, int majorLength){
        drawOneTick(majorLength, 0);
        for(int i = 1; i <= nInches; i++){
            drawTicks(majorLength -1);
            drawOneTick(majorLength, i);
        }
    }
}


class EnglishScale{
    public static void main(String args[]){
        DrawScale draw = new DrawScale();
        draw.drawRuler(1, 5);
    }
}