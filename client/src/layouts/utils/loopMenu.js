import { SettingOutlined, DatabaseOutlined } from '@ant-design/icons';

const IconMap = {
  setting: <SettingOutlined />,
  database: <DatabaseOutlined />,
};

export default function loopMenu(menuData = []) {
  return menuData.map(({ icon, children, ...item }) => ({
    ...item,
    icon: icon && IconMap[icon],
    children: children && loopMenu(children),
  }));
}
