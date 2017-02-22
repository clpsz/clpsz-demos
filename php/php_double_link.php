<?PHP


class DoubleLinkNode {
    public $key;
    public $value;
    public $next;
    public $pre;


    public function __construct() {
        $this->next = null;
        $this->pre = null;
    }
}

class DoubleLink {
    private $head;

    public function __construct() {
        $this->head = new DoubleLinkNode();
        $this->head->next = $this->head;
        $this->head->pre = $this->head;
    }

    //始终在表头插入
    public function add($key, $value) {
        $new_node = new DoubleLinkNode();

        $new_node->key = $key;
        $new_node->value = $value;
        $new_node->next = $new_node;
        $new_node->pre = $new_node;

        $this->__add($new_node, $this->head, $this->head->next);
    }

    //始终表未尾插入
    public function add_tail($key, $value) {
        $new_node = new DoubleLinkNode();

        $new_node->key = $key;
        $new_node->value = $value;
        $new_node->next = $new_node;
        $new_node->pre = $new_node;

        $this->__add($new_node, $this->head->pre, $this->head);
    }

    private function __add($new_node, $pre, $next) {
        $pre->next = $new_node;
        $new_node->pre = $pre;
        $new_node->next = $next;
        $next->pre = $new_node;
    }

    public function delete($key) {
        $deleted = 0;

        $it = $this->head->next;
        while ($it != $this->head) {
            if ($it->key == $key) {
                $this->__delete($it->pre, $it->next);
                $deleted++;
            }
            $it = $it->next;
        }

        return $deleted;
    }

    private function __delete($pre, $next) {
        $pre->next = $next;
        $next->pre = $pre;
    }

    //返回第一个匹配的key的value
    public function query($key) {
        $it = $this->head->next;
        while ($it != $this->head) {
            if ($it->key == $key) {
                return $it->value;
            }
            $it = $it->next;
        }

        return null;
    }

    public function update($key, $new_val) {
        $updated = 0;

        $it = $this->head->next;
        while ($it != $this->head) {
            if ($it->key == $key) {
                $updated++;
                $it->value = $new_val;
            }
            $it = $it->next;
        }

        return $updated;
    }

    public function show() {
        $it = $this->head->next;
        while ($it != $this->head) {
            echo $it->key . ": " . $it->value;
            echo PHP_EOL;
            $it = $it->next;
        }
        echo PHP_EOL;
    }
}


class ZppServerTmpDlinkTest extends ZppServerBaseTest{
    public function test_add() {
        $double_link = new DoubleLink();
        $double_link->add('Mercury', '3.30200×10^23');
        $double_link->add('Earth', '5.9742×10^24');
        $double_link->add('Mars', '6.4219×10^23');
        $double_link->add('Venus', '4.67×10^24');
        $double_link->add('Jupiter', '1.90×10^27');
        $double_link->add('Sun', '1.989×10^30');

        $double_link->show();
    }

    public function test_add_tail() {
        $double_link = new DoubleLink();
        $double_link->add_tail('Mercury', '3.30200×10^23');
        $double_link->add_tail('Earth', '5.9742×10^24');
        $double_link->add_tail('Mars', '6.4219×10^23');
        $double_link->add_tail('Venus', '4.67×10^24');
        $double_link->add_tail('Jupiter', '1.90×10^27');
        $double_link->add_tail('Sun', '1.989×10^30');

        $double_link->show();
    }

    public function test_delete() {
        $double_link = new DoubleLink();
        $double_link->add_tail('Mercury', '3.30200×10^23');
        $double_link->add_tail('Earth', '5.9742×10^24');
        $double_link->add_tail('Mars', '6.4219×10^23');
        $double_link->add_tail('Venus', '4.67×10^24');
        $double_link->add_tail('Jupiter', '1.90×10^27');
        $double_link->add_tail('Sun', '1.989×10^30');
        $double_link->show();

        $double_link->delete('Venus');
        $double_link->show();
        $double_link->delete('Sun');
        $double_link->show();
    }

    public function test_update() {
        $double_link = new DoubleLink();
        $double_link->add_tail('Mercury', '3.30200×10^23');
        $double_link->add_tail('Earth', '5.9742×10^24');
        $double_link->add_tail('Mars', '6.4219×10^23');
        $double_link->add_tail('Venus', '4.67×10^24');
        $double_link->add_tail('Jupiter', '1.90×10^27');
        $double_link->add_tail('Sun', '1.989×10^30');
        $double_link->show();

        $double_link->update('Mars', 'Unknown');
        $double_link->show();
    }

    public function test_query() {
        $double_link = new DoubleLink();
        $double_link->add_tail('Mercury', '3.30200×10^23');
        $double_link->add_tail('Earth', '5.9742×10^24');
        $double_link->add_tail('Mars', '6.4219×10^23');
        $double_link->add_tail('Venus', '4.67×10^24');
        $double_link->add_tail('Jupiter', '1.90×10^27');
        $double_link->add_tail('Sun', '1.989×10^30');
        $double_link->show();

        $result = $double_link->query('Mars');
        echo $result;
        echo PHP_EOL;
    }
}

