package test;


import com.plugin.code.generator.EntityToGWDataBuilder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 23/4/22
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityToGWDataBuilderTest {

    EntityToGWDataBuilder testObject ;

    @Before
    public void createObjects(){
        System.out.println("Before **************");
         testObject = new EntityToGWDataBuilder();
    }

    @Test
    public void testPackageNameValid(){
        String _sourceFile = "src/com/plugin/Sample.java";


    }

    @Test
    public void testBuilderName(){
        String _entityNameExt = "Claim_Ext";
        String _entityNameIns = "Claim_Ins";
        String _entityNameDlg = "Claim_Dlg";
        String _entityNameDefault = "Claim";

        Assert.assertEquals("ClaimBuilder_Ext",testObject.createBuilderName(_entityNameExt));
        Assert.assertEquals("ClaimBuilder_Ins",testObject.createBuilderName(_entityNameIns));
        Assert.assertEquals("ClaimBuilder_Dlg",testObject.createBuilderName(_entityNameDlg));
        Assert.assertEquals("ClaimBuilder",testObject.createBuilderName(_entityNameDefault));


    }

}
