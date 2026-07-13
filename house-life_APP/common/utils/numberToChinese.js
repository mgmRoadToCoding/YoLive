/**
 * 将阿拉伯数字转换为中文数字（简体）
 * 支持：整数、负数、小数；单位到兆
 * 示例：
 *  - 1 -> 一
 *  - 10 -> 十
 *  - 20 -> 二十
 *  - 101 -> 一百零一
 *  - 10010 -> 一万零一十
 *  - -3.5 -> 负三点五
 */

const NUMS = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
const UNITS = ['', '十', '百', '千']
const BIG_UNITS = ['', '万', '亿', '兆']

function sanitize(input) {
  if (typeof input === 'number' && !isFinite(input)) return ''
  let str = String(input).trim()
  if (!str) return ''

  let negative = false
  if (str[0] === '-') {
    negative = true
    str = str.slice(1)
  }

  // 去除千分位和空格
  str = str.replace(/[,，\s]/g, '')
  if (!/^\d+(\.\d+)?$/.test(str)) return ''

  let [intStr, decStr = ''] = str.split('.')
  // 去除整数部分前导零（保留单个零）
  intStr = intStr.replace(/^0+(?=\d)/, '')
  if (intStr === '') intStr = '0'

  return { intStr, decStr, negative }
}

// 处理四位以内的片段，如 0012 -> 十二，1030 -> 一千零三十
function fourToChinese(segment) {
  const digits = segment.split('').map(Number)
  let res = ''
  const firstNonZeroIdx = digits.findIndex(d => d !== 0)

  for (let i = 0; i < digits.length; i++) {
    const d = digits[i]
    const pos = digits.length - i - 1 // 3->千, 2->百, 1->十, 0->个

    if (d === 0) {
      // 后面还有非零且当前结果不以零结尾，补一个“零”
      if (i < digits.length - 1 && digits.slice(i + 1).some(x => x !== 0) && !res.endsWith('零')) {
        res += '零'
      }
    } else {
      // 片段开头就是“十”且为 1x 的情况，省略“一” -> 十X
      if (pos === 1 && d === 1 && i === firstNonZeroIdx) {
        res += '十'
      } else {
        res += NUMS[d] + UNITS[pos]
      }
    }
  }

  return res
}

function intToChinese(intStr) {
  const intVal = Number(intStr)
  if (intVal === 0) return NUMS[0]
  if (intVal > 0 && intVal < 10) return NUMS[intVal]
  if (intVal >= 10 && intVal < 20) {
    const last = intVal % 10
    return '十' + (last ? NUMS[last] : '')
  }

  // 分组（每四位一组）
  const padded = intStr.padStart(Math.ceil(intStr.length / 4) * 4, '0')
  const groups = []
  for (let i = 0; i < padded.length; i += 4) {
    groups.push(padded.slice(i, i + 4))
  }

  let res = ''
  for (let i = 0; i < groups.length; i++) {
    const g = groups[i]
    const trimmed = g.replace(/^0+/, '') // 去掉组内前导零以简化处理
    const groupRes = trimmed ? fourToChinese(trimmed.padStart(g.length, '0')) : ''
    const unit = BIG_UNITS[groups.length - i - 1]

    if (groupRes) {
      res += groupRes + unit
    } else {
      // 整组为 0000，若后面还有非零组且当前不以零结尾，则补一个“零”
      if (res && !res.endsWith('零') && groups.slice(i + 1).some(x => x !== '0000')) {
        res += '零'
      }
    }
  }

  // 去除末尾的“零”
  res = res.replace(/零+$/, '')
  return res || NUMS[0]
}

function numberToChinese(input) {
  const s = sanitize(input)
  if (!s) return ''
  const { intStr, decStr, negative } = s

  let res = intToChinese(intStr)
  if (decStr) {
    // 小数部分逐位读
    res += '点' + decStr.split('').map(d => NUMS[Number(d)]).join('')
  }
  if (negative && res) res = '负' + res
  return res
}

export default numberToChinese