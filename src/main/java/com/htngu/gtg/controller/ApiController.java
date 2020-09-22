package com.htngu.gtg.controller;

import com.htngu.gtg.data.dataproc.PostProc;
import com.htngu.gtg.entity.Account;
import com.htngu.gtg.entity.Post;
import com.htngu.gtg.entity.PostDetail;
import com.htngu.gtg.repository.AccountRepository;
import com.htngu.gtg.data.response.CommonResponse;
import com.htngu.gtg.repository.PostRepository;
import com.htngu.gtg.service.appservice.AppServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@RestController
public class ApiController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    AppServiceImp appServiceImp;
    @Autowired
    PostRepository postRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> hello(){
        return CommonResponse.of(true, "ok", "xin chao");

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> login(@RequestBody Account account){
        Account account1 = accountRepository.login(account.getUserName(), account.getPassword());
        if (account1!=null){
            return CommonResponse.of(true, "Login success", account1);
        }else return CommonResponse.of(false, "user name or password not correct", null);

    }

    @RequestMapping(path = "/phone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> checkPhone(@RequestParam String phone){
        Account account = accountRepository.checkPhone(phone);
        if (account!=null){
            return CommonResponse.of(true, "phone exist", true);
        }else return CommonResponse.of(false, "phone not exist", false);

    }

    @RequestMapping(path = "/get-avatar", method = RequestMethod.GET, produces = {MediaType.IMAGE_PNG_VALUE})
    public Object getImage(@RequestParam(name = "id") int id) throws IOException {
        Account account = accountRepository.findById(id).get();
        File img = new File(account.getAvatar());
        System.out.println(img.toPath());
        byte[] bytes = Files.readAllBytes(img.toPath());
        if (bytes != null)
            return bytes;
        else
            return CommonResponse.of(false, "get avatar fail", null);
    }

    @RequestMapping(value = "/upload-avatar", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResponse<Object> postAvatar(@RequestPart(name = "file") MultipartFile file, String id) {

        try {
            String folderPath = new File(".").getAbsolutePath();
            folderPath += "\\data\\avatar\\"+id+"\\";
            File dir = new File(folderPath);
            // Nếu chưa tồn tại thư mục upload thì tạo mới
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            File filePath = new File(folderPath + fileName);
            file.transferTo(filePath);
            System.out.println("============ Save file success: "+filePath +"============");
            Account account = new Account();
            if((folderPath + fileName)==null)
                account.setAvatar("default.jpg");
            else
                account.setAvatar(folderPath + fileName);

            return CommonResponse.of(true, "post avatar success", getImage(account.getId()));

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResponse.of(false, "post avatar fail", null);
    }
    @RequestMapping(path = "/register", method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResponse<Account> register(@RequestBody Account account)
    {
        if(account != null){
            return CommonResponse.of(true, "Register success", accountRepository.save(account));
        }
        return CommonResponse.of(false, "Register fail" ,null);
    }

    @RequestMapping(path = "/update-password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> updatePass(@RequestParam String phone, @RequestParam String pass){
        accountRepository.updatePass(phone, pass);
        Account account = accountRepository.findByPhone(phone);
        if(account.getPassword().equals(pass)){
            return CommonResponse.of(true, "Update success", account);
        }
        return CommonResponse.of(false, "Update fail", null);
    }
    
    @RequestMapping(path="/get-post-by-id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<PostProc> getPostByID(@RequestParam long id){
        PostProc postProc = new PostProc();
        postProc.setData(appServiceImp.getPostById(id));
        return CommonResponse.of(true, "get success", postProc);
    }
    @RequestMapping(path = "/get-all-post-with-user-info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> getAllPost(){
        return CommonResponse.of(true, "get all success", appServiceImp.getAllPost());
    }

//    @RequestMapping(path = "/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public CommonResponse<Object> post(@RequestBody Post post){
//        post.setHiddenFlag(false);
//        postRepository.save(post);
//        System.out.println("============"+post.getId()+"==========");
//        return CommonResponse.of(true, "post success", null);
//    }

    @RequestMapping(path = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<ArrayList<Account>> test(){
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(Account.of(1, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(2, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(3, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(4, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(5, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(6, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(7, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(8, "Hoàng Triệu Ngũ", "sdajshdah", "010031041", "hhtja@gmail.com", "lạng sơn", "admin", null, null, null));
        accounts.add(Account.of(9, "Vi Ngọc Ánh", "yeuemladieuanhkhongthe", "0393496466", "yeuam@gmail.com", "Thái Nguyên", "ADM", null, null, null));

        return CommonResponse.of(true, "get success", accounts);
    }


    @RequestMapping(path = "/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> post(@RequestBody Post post){
        if (post!=null){
            postRepository.save(post);
            return CommonResponse.of(true, "upload success", post);
        }else return CommonResponse.of(false, "upload fail", null);
    }

    @RequestMapping(path = "/post-images", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Object> postImage(@RequestParam("id") Integer id, @RequestParam ArrayList<MultipartFile> file){
        Post post = postRepository.getOne(id);
        if (post!=null){
            post.getDetail().setImages(upImage(post.getId(), file));
            postRepository.save(post);
            return CommonResponse.of(true, "upload success", post);
        }else return CommonResponse.of(false, "upload fail", null);
    }

    @RequestMapping(path = "/get-post-image", method = RequestMethod.GET, produces = {MediaType.IMAGE_PNG_VALUE})
    public Object getPostImage(@RequestParam(name = "id") int id) throws IOException {
        Post post = postRepository.findById(id).get();
        String s = post.getDetail().getImages();
//        String[] images  = s.split(";");
//
//        byte[][] bytes = new byte[images.length][];
//        for (int i = 0; i < images.length; i++){
//            File img = new File(images[i]);
//            System.out.println(img.toPath());
//            bytes[i] = Files.readAllBytes(img.toPath());
//        }
        File file = new File(s.replace(";", ""));
        byte[] bytes = Files.readAllBytes(file.toPath());


        if (bytes != null)
            return bytes;
        else
            return CommonResponse.of(false, "get images fail", null);
    }


    public String upImage(Integer postID, ArrayList<MultipartFile> files) {
        try {
            String folderPath = new File(".").getAbsolutePath();
            folderPath += "\\data\\images\\"+postID+"\\";
            File dir = new File(folderPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String productImages = "";
            for (MultipartFile file : files){
                // Lấy ra tên file vừa gửi từ client lên
                String fileName = file.getOriginalFilename();
                // Sử dụng đối tượng File của java.io để lưu file
                File filePath = new File(folderPath + fileName);
                file.transferTo(filePath);
                System.out.println("============ Save file success: "+filePath +" ============");
                if(filePath!=null)
                    productImages+=filePath+";";
            }
            return productImages;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }


}
