public  class  Sync {
    private  static  final  Object  lock = new Object ( ) ;
        public  static  void  battleFinished ()  {
            synchronized  ( lock ) {
                lock . notifyAll ( ) ;
            }
        }
        public  static  void  waitForBattleEnd ()  {
            synchronized  ( lock ) {
                try  {
                    lock . wait ( ) ;
                }  catch  ( InterruptedException  ignored ) {
            }
        }
    }
}