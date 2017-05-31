import selection.*;

import java.util.Set;

import static org.junit.Assert.assertTrue;


/**
 * Runs the four selection methods against a given
 * dataset. This is a useful class for running all the
 * code at once and checking the output.
 *
 * The only tests this actually does is checks
 * the size of the subsets returned from the numFeatures
 * methods is less than or equal to the specified size.
 */

public class TestAll {

    // Since weka treats attributes and classes uniformly, must explicitly state class indiex
    private final int CLASS_INDEX = 166;

    // File of instances to use
    private final String FILE_NAME = "musk.arff";

    // Maximum number of features to select
    private final int MAX_FEATURES = 50;

    /***
     * ===============
     * SFS TESTS
     * ===============
     */

    @org.junit.Test
    public void testSequentialForwardSelection() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential forward selection");
        FeatureSelection selector = generateSelector(Selection.SFS);
        Set<Integer> selectedIndices = selector.select();
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
    }

    @org.junit.Test
    public void testSequentialForwardSelectionNumfeatures() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential forward selection for max " + MAX_FEATURES + " features");
        FeatureSelection selector = generateSelector(Selection.SFS);
        Set<Integer> selectedIndices = selector.select(MAX_FEATURES);
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
        assertTrue(selectedIndices.size() <= MAX_FEATURES);
    }

    /***
     * ===============
     * SBS TESTS
     * ===============
     */

    @org.junit.Test
    public void testSequentialBackwardSelection() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential backward selection");
        FeatureSelection selector = generateSelector(Selection.SBS);
        Set<Integer> selectedIndices = selector.select();
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
    }

    @org.junit.Test
    public void testSequentialBackwardSelectionNumfeatures() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential backward selection for max " + MAX_FEATURES + " Features");
        FeatureSelection selector = generateSelector(Selection.SBS);
        Set<Integer> selectedIndices = selector.select(MAX_FEATURES);
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
        assertTrue(selectedIndices.size() <= MAX_FEATURES);
    }

    /***
     * ===============
     * FLOATING TESTS
     * ===============
     */

    @org.junit.Test
    public void testSequentialFloatingForwardSelection() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential floating forward selection");
        FeatureSelection selector = generateSelector(Selection.SFFS);
        Set<Integer> selectedIndices = selector.select();
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
    }

    @org.junit.Test
    public void testSequentialFloatingForwardSelectionNumFeatures() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential floating forward selection for " + MAX_FEATURES + " features");
        FeatureSelection selector = generateSelector(Selection.SFFS);
        Set<Integer> selectedIndices = selector.select(MAX_FEATURES);
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
        assertTrue(selectedIndices.size() <= MAX_FEATURES);

    }

    @org.junit.Test
    public void testSequentialFloatingBackwardSelection() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential backward floating selection");
        FeatureSelection selector = generateSelector(Selection.SFBS);
        Set<Integer> selectedIndices = selector.select();
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
    }

    @org.junit.Test
    public void testSequentialFloatingBackwardSelectionNumFeatures() throws Exception {
        System.out.println("-------------------");
        System.out.println("Sequential backward floating selection for " + MAX_FEATURES + " features");
        FeatureSelection selector = generateSelector(Selection.SFBS);
        Set<Integer> selectedIndices = selector.select(MAX_FEATURES);
        selector.compareTestingAccuracy(selectedIndices);
        System.out.println("-------------------");
        assertTrue(selectedIndices.size() <= MAX_FEATURES);
    }

    /***
     * ===============
     * HELPER METHODS
     * ===============
     */

    private FeatureSelection generateSelector(Selection method) throws Exception {
        FeatureSelection selector = null;
        switch (method){
            case SBS:
                selector = new SequentialBackwardSelection(FILE_NAME, CLASS_INDEX);
                break;
            case SFS:
                selector = new SequentialForwardSelection(FILE_NAME, CLASS_INDEX);
                break;
            case SFBS:
                selector = new SequentialFloatingBackwardSelection(FILE_NAME, CLASS_INDEX);
                break;
            case SFFS:
                selector = new SequentialFloatingForwardSelection(FILE_NAME, CLASS_INDEX);
                break;
        }

        // Special case for musk
        if(FILE_NAME.equals("musk.arff")){
            // There is a "giveaway" feature (molecule_name) which stores some class information
            selector.removeAttribute(0);
        }

        return selector;
    }

    private enum Selection {
        SFS,
        SBS,
        SFFS,
        SFBS
    }


}
