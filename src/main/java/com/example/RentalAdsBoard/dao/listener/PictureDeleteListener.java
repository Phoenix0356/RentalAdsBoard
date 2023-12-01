//package com.example.RentalAdsBoard.dao.listener;
//
//import com.example.RentalAdsBoard.entity.Picture;
//import com.example.RentalAdsBoard.entity.User;
//import com.example.RentalAdsBoard.util.DataUtil;
//import org.hibernate.event.spi.PostDeleteEvent;
//import org.hibernate.event.spi.PostDeleteEventListener;
//import org.hibernate.persister.entity.EntityPersister;
//
//
//public class PictureDeleteListener implements PostDeleteEventListener {
//    @Override
//    public void onPostDelete(PostDeleteEvent event) {
//        Object entity = event.getEntity();
//        try {
//            if (entity instanceof User) DataUtil.deleteImage(((User) entity).getAvatarPath());
//            else if (entity instanceof Picture) DataUtil.deleteImage(((Picture) entity).getPath());
//
//        }catch (Exception e){
//            return;
//        }
//    }
//
//    @Override
//    public boolean requiresPostCommitHanding(EntityPersister persister) {
//        return false;
//    }
//}
